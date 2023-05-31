create table account_info (
    account_id int generated always as identity,
    account_email varchar(50) not null unique,
    account_username varchar(20) not null unique,
    account_mobile_number varchar(17) default null,
    account_hashed_pass bpchar(60) not null,

    constraint cnst_email_validate_format
        check (account_email ~ '^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$'),

    constraint cnst_username_validate_format
        check (account_username ~ '^[a-z0-9_]*$'),
    constraint cnst_username_minimun_length
        check (length(account_username) >= 3),

    constraint cnst_mobile_number_format
        check (account_mobile_number ~ '^([+]\d{2,3}[ ])?\d{9,10}$'),

    primary key (account_id)
);

create function lower_case_username_and_email() returns trigger
as $BODY$ begin
    new.account_username = lower(regexp_replace(new.account_username, '[\s+]', '', 'g'));
    new.account_email = lower(regexp_replace(new.account_email, '[\s+]', '', 'g'));
    if(new.account_mobile_number is not null) then
        new.account_mobile_number = concat('+', regexp_replace(new.account_mobile_number, '[\s+]', '', 'g'));
    end if;
    return new;
end $BODY$ language plpgsql;

create trigger trigger_lower_case_username_and_email before insert or update
on account_info for each row execute procedure lower_case_username_and_email();

create table account_management (
    account_id int,
    account_role varchar(10) not null default 'Anonymous',

    account_non_expired boolean not null default true,
    account_non_locked boolean not null default true,
    account_credentials_non_expired boolean not null default true,
    account_enabled boolean not null default false,

    account_activate_date timestamp,
    account_credentials_expiration_date timestamp,

    primary key (account_id),
    foreign key (account_id) references account_info(account_id) on delete cascade
);

create table locked_account (
    account_id int,

    lock_reason text not null,
    lock_issued_date timestamp not null default current_timestamp,
    lock_issued_by int not null,

    primary key (account_id),
    foreign key (account_id) references account_info(account_id) on delete cascade,
    foreign key (lock_issued_by) references account_info(account_id)
);

create table account_sensitive_request (
    request_id bigint generated always as identity,
    request_token uuid not null default gen_random_uuid(),
    request_type char(3) not null,
    request_user_confirmation boolean not null default false,

    account_id int,

    constraint cnst_request_type_validate_type check (left(request_type, 1) in ('A', 'R', 'V')),

    primary key (request_id, request_type),
    foreign key (account_id) references account_info(account_id) on delete cascade
);

create table user_info (
    user_id int generated always as identity,
    user_display_name varchar(20) not null,
    user_avatar varchar(80),
    user_gender char(1) not null default 'x',
    --TODO: user_primary boolean not null,

    account_id int,

    --TODO: contranit cnst_only_one_primary_user

    primary key (user_id),
    foreign key (account_id) references account_info(account_id) on delete cascade
);

create table user_settings (
    user_id int,

    display_theme char(1) not null default 's',
    valid_login_days int2 not null default 7,
    enable_two_step_verify boolean not null default false,
    preferred_language varchar(5) not null default 'en-us'

    constraint cnst_max_valid_login_days check (valid_login_days <= 31),
    constraint cnst_min_valid_login_days check (valid_login_days >= 1),

    constraint cnst_valid_locale_string 
        check (preferred_language ~ '^[a-z]{2}(([_-]{1})([a-zA-Z]{2}){1,2})$'),

    primary key (user_id),
    foreign key (user_id) references user_info(user_id) on delete cascade
);

create table account_permissions (
    account_id int,

    permission_mobile_message boolean not null default false,
    permission_email_message boolean not null default true,
    permission_multiple_devices boolean not null default false,

    cookies_permit_analysis boolean not null default true,
    cookies_permit_advertisment boolean not null default true,
    cookies_permit_third_party boolean not null default true,

    primary key (account_id),
    foreign key (account_id) references account_info(account_id) on delete cascade
);

create table web_sessions (
    session_id uuid default gen_random_uuid(),
    session_start_timestamp timestamp not null default current_timestamp,
    session_ending_timestamp timestamp not null,
    account_id int,
    device_inet cidr not null,
    device_client_user_agent varchar(120) not null default '--User Agent Not Available--',

    primary key (session_id),
    foreign key (account_id) references account_info(account_id)
);

create function calculate_session_ending_timestamp() returns trigger as $BODY$ begin
    new.session_ending_timestamp = current_timestamp + interval '1' day *
    (select valid_login_days from user_settings where account_id = new.account_id);
    return new;
end $BODY$ language plpgsql;

create trigger trigger_calculate_session_ending_timestamp before insert
on web_sessions for each row execute procedure calculate_session_ending_timestamp();
