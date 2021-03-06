create table resume(
    uuid_field char(36) not null constraint resume_pk primary key,
    full_name text not null
);

create table contact
(
  id          serial   not null
    constraint contact_pk
      primary key,
  type        text     not null,
  value       text     not null,
  resume_uuid char(36) not null references resume (uuid_field) on delete cascade
);

create unique index contact_type_uuid_index
  on contact (resume_uuid, type);

create table section (
  id          serial primary key,
  resume_uuid char(36) not null references resume (uuid_field) on delete cascade,
  type        text     not null,
  content     text     not null
);

create unique index section_idx
  on section (resume_uuid, type);