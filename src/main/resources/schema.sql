DROP TABLE IF EXISTS "exercises";
DROP TABLE IF EXISTS "muscle_groups" CASCADE;
DROP SEQUENCE IF EXISTS muscle_groups_id_seq;

CREATE SEQUENCE IF NOT EXISTS muscle_groups_id_seq
INCREMENT BY 1
MINVALUE 1
MAXVALUE 9223372036854775807
START WITH 13
CACHE 1;

CREATE TABLE IF NOT EXISTS "muscle_groups" (
	"muscle_group_id" int8 PRIMARY KEY,
	"name" text NOT NULL,
	CONSTRAINT "ux_muscle_groups_name" UNIQUE ("name")
);