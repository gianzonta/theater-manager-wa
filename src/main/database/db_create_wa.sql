


-- Create new data types

CREATE TYPE USERTYPE AS ENUM (

	'Actor',

	'Crew',

	'Director',

	'Company Manager'

);



CREATE TYPE PRIVACY AS ENUM (

	'Public',

	'Uni',

	'Pro',

	'Private'

);



-- Create domains

CREATE DOMAIN EMAILDOM AS VARCHAR

  	CHECK ( value ~ '^[a-zA-Z0-9.!#$%&''*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$' );



CREATE DOMAIN PHONENUMDOM AS VARCHAR

	CHECK ( value ~ '^[+]?[0-9]{1,32}$'); 



CREATE DOMAIN NAMEDOM AS VARCHAR

	CHECK ( value !~ '^\s|\s$'); --don't want leading or trailing whitespaces in some sensitive fields


----------------------- only from here onward for update

DROP SCHEMA IF EXISTS theater_wa_db CASCADE;
CREATE SCHEMA theater_wa_db;

-- Member

CREATE TABLE theater_wa_db.Member (

	Username NAMEDOM,

	PswHash VARCHAR NOT NULL DEFAULT '157225318db85b078052ff9dcb582d1beb8836192e2066977a4f042fcb830341', -- hashcode for "defaultpsw"

	Name NAMEDOM NOT NULL,

	Surname NAMEDOM NOT NULL,

	Photo VARCHAR,

	Birthdate DATE CHECK ( Birthdate <= CURRENT_DATE),

	PhoneNumber PHONENUMDOM,

	Email EMAILDOM,

	HiringDate DATE NOT NULL CHECK ( HiringDate <= CURRENT_DATE),

	IsUnipdStudent BOOLEAN DEFAULT FALSE,

	ISMemberPro BOOLEAN DEFAULT FALSE,

	UserGroup USERTYPE NOT NULL,

	--enabled BOOLEAN NOT NULL DEFAULT TRUE ,

	PRIMARY KEY (Username)

);



-- Department

CREATE TABLE theater_wa_db.Department (

	Name NAMEDOM, 

	Description VARCHAR,

	PRIMARY KEY (Name)

);



-- IsPartOf

CREATE TABLE theater_wa_db.IsPartOf (

	Username NAMEDOM, 

	Name NAMEDOM,

	PRIMARY KEY (Username,Name),

	FOREIGN KEY (Username) REFERENCES theater_wa_db.Member(Username) ON DELETE CASCADE,

	FOREIGN KEY (Name) REFERENCES theater_wa_db.Department(Name) ON DELETE CASCADE

);



-- Season

CREATE TABLE theater_wa_db.Season (

	SeasonID SERIAL,

	Period DATERANGE NOT NULL,

	Revenues NUMERIC(10,2) DEFAULT 0 NOT NULL CHECK (Revenues>=0),

	Expenses NUMERIC(10,2) DEFAULT 0 NOT NULL CHECK (Expenses>=0), 

	InitialFund NUMERIC(10,2) DEFAULT 0 NOT NULL,

	PRIMARY KEY(SeasonID)

);



-- Play

CREATE TABLE theater_wa_db.Play (

	PlayID SERIAL,

	Title NAMEDOM NOT NULL,

	Description VARCHAR,

	Script VARCHAR,

	Duration INTEGER CHECK (Duration >=0),

	PosterImage VARCHAR,

	RehearsalSchedule VARCHAR,

	PRIMARY KEY(PlayID)

);



-- UserRole

CREATE TABLE theater_wa_db.UserRole (

	Username NAMEDOM,

	SeasonID INTEGER,

	PlayID INTEGER,

	Name NAMEDOM,

	Role NAMEDOM,

	PRIMARY KEY (Username,SeasonID,PlayID,Name),

	FOREIGN KEY (Username) REFERENCES theater_wa_db.Member(Username) ON DELETE CASCADE,

	FOREIGN KEY (SeasonID) REFERENCES theater_wa_db.Season(SeasonID) ON DELETE CASCADE,

	FOREIGN KEY (PlayID) REFERENCES theater_wa_db.Play(PlayID) ON DELETE CASCADE,

	FOREIGN KEY (Name) REFERENCES theater_wa_db.Department(Name) ON DELETE CASCADE

);



-- Item

CREATE TABLE theater_wa_db.Item (

	ItemID SERIAL,

	Name NAMEDOM NOT NULL,

	Description VARCHAR,

	Quantity INTEGER,

	Size VARCHAR,

	HistoricalGenre VARCHAR,

	Image VARCHAR,

	Username NAMEDOM NOT NULL,

	PRIMARY KEY (ItemID),

	FOREIGN KEY (Username) REFERENCES theater_wa_db.Member(Username)

);





-- Belongs

CREATE TABLE theater_wa_db.Belongs (

	ItemID INTEGER,

	Name NAMEDOM,

	PRIMARY KEY (ItemID, Name),

	FOREIGN KEY (ItemID) REFERENCES theater_wa_db.Item(ItemID) ON DELETE CASCADE,

	FOREIGN KEY (Name) REFERENCES theater_wa_db.Department(Name) ON DELETE CASCADE

);





-- Uses

CREATE TABLE theater_wa_db.Uses (

	ItemID INTEGER,

	PlayID INTEGER,

	PRIMARY KEY (ItemID, PlayID),

	FOREIGN KEY (ItemID) REFERENCES theater_wa_db.Item(ItemID) ON DELETE CASCADE,

	FOREIGN KEY (PlayID) REFERENCES theater_wa_db.Play(PlayID) ON DELETE CASCADE

);





-- Transaction (CAVEAT: it's a reserved keyword in SQL-92)

CREATE TABLE theater_wa_db.Transact (

	TransID SERIAL,

	Name NAMEDOM NOT NULL,

	Description VARCHAR,

	Amount NUMERIC(10,2) NOT NULL,

	Invoice VARCHAR,

	TransactionDate DATE NOT NULL CHECK ( TransactionDate <= CURRENT_DATE),

	Username NAMEDOM NOT NULL,

	SeasonID INTEGER NOT NULL,

	PRIMARY KEY (TransID),

	FOREIGN KEY (Username) REFERENCES theater_wa_db.Member(Username),

	FOREIGN KEY (SeasonID) REFERENCES theater_wa_db.Season(seasonID)

);



-- Acquires

CREATE TABLE theater_wa_db.Acquires (

	ItemID INTEGER,

	TransID INTEGER,

	PRIMARY KEY (ItemID,TransID),

	FOREIGN KEY (ItemID) REFERENCES theater_wa_db.Item(ItemID) ON DELETE CASCADE,

	FOREIGN KEY (TransID) REFERENCES theater_wa_db.Transact(TransID) ON DELETE CASCADE

);



-- Activity

CREATE TABLE theater_wa_db.Activity (

	ActivityID SERIAL,

	Title NAMEDOM NOT NULL,

	Description VARCHAR,

	Type NAMEDOM,

	Location VARCHAR NOT NULL,

	DateAndTime TIMESTAMP WITH TIME ZONE NOT NULL,

	PrivacyTag PRIVACY DEFAULT 'Public',

	MaxAudience INTEGER,

	AudienceSize INTEGER,

	SeasonID INTEGER NOT NULL,

	PRIMARY KEY (ActivityID),

	FOREIGN KEY (SeasonID) REFERENCES theater_wa_db.Season(SeasonID)

);



-- Schedule

CREATE TABLE theater_wa_db.Schedule (

	PlayID INTEGER,

	ActivityID INTEGER,

	PRIMARY KEY (PlayID,ActivityID),

	FOREIGN KEY (PlayID) REFERENCES theater_wa_db.Play(PlayID) ON DELETE CASCADE,

	FOREIGN KEY (ActivityID) REFERENCES theater_wa_db.Activity(ActivityID) ON DELETE CASCADE

);



-- GeneratedBy

CREATE TABLE theater_wa_db.GeneratedBy (

	TransID INTEGER,

	ActivityID INTEGER,

	PRIMARY KEY (TransID,ActivityID),

	FOREIGN KEY (TransID) REFERENCES theater_wa_db.Transact(TransID) ON DELETE CASCADE,

	FOREIGN KEY (ActivityID) REFERENCES theater_wa_db.Activity(ActivityID) ON DELETE CASCADE

);



-- ExternalPartecipant

CREATE TABLE theater_wa_db.ExternalPartecipant (

	PartecipantID SERIAL,

	Name NAMEDOM NOT NULL,

	Surname NAMEDOM NOT NULL,

	PhoneNumber PHONENUMDOM,

	Email EMAILDOM,

	PRIMARY KEY (PartecipantID)

);



-- Attend

CREATE TABLE theater_wa_db.Attend (

	ActivityID INTEGER,

	PartecipantID INTEGER,

	PRIMARY KEY (ActivityID,PartecipantID),

	FOREIGN KEY (ActivityID) REFERENCES theater_wa_db.Activity(ActivityID) ON DELETE CASCADE,

	FOREIGN KEY (PartecipantID) REFERENCES theater_wa_db.ExternalPartecipant(PartecipantID) ON DELETE CASCADE

);











