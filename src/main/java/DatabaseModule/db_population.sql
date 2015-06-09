/* insert enums */
INSERT into enum (table_name, column_name, enum_code, enum_value)
    VALUES ('p_communityMember', 'sex', 0, 'male'), ('p_communityMember', 'sex', 1, 'female')

INSERT into enum (table_name, column_name, enum_code, enum_value)
    VALUES ('registrationFields', 'fields_group', 0, 'personal'),
      ('registrationFields', 'fields_group', 1, 'medical'),
      ('registrationFields', 'fields_group', 2, 'preferences '),
      ('registrationFields', 'fields_group', 3, 'professional ')



INSERT into enum (table_name, column_name, enum_code, enum_value)
VALUES ('registrationFields', 'type', 0, 'string'),
  ('registrationFields', 'type', 1, 'int'),
  ('registrationFields', 'type', 2, 'float'),
  ('registrationFields', 'type', 3, 'datetime'),
  ('registrationFields', 'type', 4, 'timestamp');

insert into registrationFields (field_name, type, user_type, fields_group, needs_verification, is_required, max_length, get_possible_values_from, serial_num, insert_data_to, refresh_time, gui_description) values
  ('email_address', 0, 0, 0, 1, 1, 50, null, 0, 'p_communityMembers', null, 'Email Address'),
  ('first_name', 0, 0, 0, 1, 1, 50, null, 1, 'p_communityMembers', null, 'First Name'),
  ('last_name', 0, 0, 0, 1, 1, 50, null, 2, 'p_communityMembers', null, 'Last Name'),
  ('birth_date', 3, 0, 0, 1, 1, 50, null, 3, 'p_communityMembers', null, 'Birth Date'),
  ('state', 0, 0, 0, 1, 1, 50, null, 4, 'p_communityMembers', null, 'State'),
  ('password', 0, 0, 0, 1, 1, 50, null, 5, 'p_communityMembers', null, 'Password'),
  ('medical_condition_id', 1, 0, 1, 1, 1, 50, 'm_medicalConditions', 6, 'P_Diagnosis', null, 'Medical Condition ID'),
  ('medication_num', 1, 0, 1, 1, 1, 50, 'P_Medications', 7, 'P_prescriptions', null, 'Medication ID'),
  ('P_supervision.doc_license_num', 1, 0, 1, 1, 1, 50, null, 8, null, null, 'Supervising doctor license number'),
  ('P_diagnosis.doc_license_num', 1, 0, 1, 1, 1, 50, null, 9, null, null, 'Diagnosing doctor license number'),
  ('P_prescriptions.doc_license_num', 1, 0, 1, 1, 1, 50, null, 10, null, null, 'Prescribing doctor license'),
  ('email_address', 0, 1, 0, 1, 1, 50, null, 0, 'p_communityMembers', null, 'Email Address'),
  ('first_name', 0, 1, 0, 1, 1, 50, null, 1, 'p_communityMembers', null, 'First Name'),
  ('last_name', 0, 1, 0, 1, 1, 50, null, 2, 'p_communityMembers', null, 'Last Name'),
  ('birth_date', 3, 1, 0, 1, 1, 50, null, 3, 'p_communityMembers', null, 'Birth Date'),
  ('state', 0, 1, 0, 1, 1, 50, null, 4, 'p_communityMembers', null, 'State'),
  ('password', 0, 1, 0, 1, 1, 50, null, 5, 'p_communityMembers', null, 'Password'),
  ('doc_license_number', 1, 1, 1, 1, 1, 50, null, 10, null, null, 'Doctor license'),
  ('organization_id', 1, 1, 1, 1, 1, 50, null, 10, null, null, 'Doctor license'),
  ('position_num', 1, 1, 1, 1, 1, 50, null, 10, null, null, 'Doctor license'),
  ('certification_external_id', 1, 1, 1, 1, 1, 50, null, 10, null, null, 'Doctor license'),
  ('specialization_id', 1, 1, 1, 1, 1, 50, null, 10, null, null, 'Doctor license')


delete from dbo.RegistrationFields
