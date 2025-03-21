DROP TABLE IF EXISTS "exercises";
DROP TABLE IF EXISTS "muscle_groups" CASCADE;
CREATE SEQUENCE IF NOT EXISTS "muscle_groups_id_seq" INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

CREATE TABLE IF NOT EXISTS "muscle_groups" (
	"muscle_group_id" int8 PRIMARY KEY,
	"name" text NOT NULL,
	CONSTRAINT "ux_muscle_groups_name" UNIQUE ("name")
);