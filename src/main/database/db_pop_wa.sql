

















-- INSERT OPERATION FOR member TABLE



INSERT INTO theater_wa_db.member (username, pswhash, name, surname, photo, birthdate, phonenumber, email, hiringdate, isunipdstudent, ismemberpro, usergroup)



VALUES  ('lucas', '779606ec793433bcada0f35f916e108497b650ba9fb56329ff496c73d1436a92', 'Lucas', 'Moura', 'https://www.microsoft.com/en-us/research/uploads/prod/2018/01/profile_square.jpg', '1992-04-26', 32156787312, 'lucas@domain.com', '2018-04-26', 'true', DEFAULT, 'Director'),



        ('linda', '6f5e97d18a132b3a9e813de94d11bd8be1e44589380c5cd7bf32a48bc4df75dc', 'Linda', 'Brown', 'https://dieteticallyspeaking.com/wp-content/uploads/2017/01/profile-square.jpg', '1993-08-13', 35198786218, 'linda@domain.com', '2018-04-04', DEFAULT, 'true', 'Crew'),



        ('allen', '89da51d17b315efc31eca97e4323ec6a82b5e3422af335b0488537c802ba588d', 'Allen', 'Smith', 'https://media.creativemornings.com/uploads/user/avatar/59728/mv-profile-image-square.jpg  ', '1991-12-10', 35198781889, 'allen@domain.com', '2019-12-06', DEFAULT, 'true', 'Crew'),



        ('kirsten', '77268383f5a24c33828c1f5777fdd3944c961bba19f8d6d3691a94cf3ff9971f', 'Kirsten', 'Murphy', 'https://i0.wp.com/www.sardiniauniqueproperties.com/wp-content/uploads/2015/10/Square-Profile-Pic-1.jpg', '1996-11-20', 35193286218, 'kirsten@domain.com', '2018-10-14', DEFAULT, 'true', 'Actor'),



        ('liam', '417aafa9929c0e0eec977109c3949abe17540a1542f8266e354715bd65b6b570', 'Liam', 'Stewart', 'https://media.creativemornings.com/uploads/user/avatar/73303/Holtning-Profile-Square-Site.png', '1994-01-14', 35198786290, 'liam@domain.com', '2019-11-25', DEFAULT, 'true', 'Actor'),


        ('cm', 'b5039c325d7107d60895787cd759095631d3fe594913e6ff284434b254a46b4c', 'Mr.Company', 'Manager Jr.', 'https://media.creativemornings.com/uploads/user/avatar/73303/Holtning-Profile-Square-Site.png', '1994-01-14', 35198786290, 'liam@domain.com', '2019-11-25', DEFAULT, 'true', 'Company Manager');




-- INSERT OPERATION FOR department TABLE



INSERT INTO theater_wa_db.department (name, description)



VALUES  ('marketing', 'marketing'),



        ('hair', 'hair design'),



        ('costume', 'costume department'),



        ('scenography', 'scenography'),



        ('playwriting', 'playwriting department');







-- INSERT OPERATION FOR ispartof RELATIONSHIP



INSERT INTO theater_wa_db.ispartof (username, name)



VALUES  ('lucas', 'marketing'),



        ('linda', 'hair'),



        ('allen', 'scenography'),



        ('kirsten', 'costume'),



        ('liam', 'marketing');







-- INSERT OPERATION FOR season TABLE



INSERT INTO theater_wa_db.season (seasonid, period, revenues, expenses, initialfund)



VALUES  (20192020, '[2019-10-01,2020-09-30)', 200.00, 100.00,  100.00),



        (20202021, '[2020-10-01,2021-09-30)', 300.00, 250.00, 100.00),



        (20182019, '[2018-10-01,2019-09-30)',200.00, 100.00,  100.00);











-- INSERT OPERATION FOR item TABLE



INSERT INTO theater_wa_db.item (itemid, name, description, quantity, size, historicalgenre, image, username)



VALUES  (200, 'Iphone 8', 'Iphone 8 2020', 20, '4.7 inch', NULL, 'https://bit.ly/3cOEGHy', 'lucas'),



        (201, 'Hair wax', 'Hair wax made in Italy', 30, '3 inch', NULL, 'https://bit.ly/3cOEGHy', 'linda'),



        (202, 'Classic dress', 'Classical dress suitable for the playid=1', 3, 'medium', NULL, 'https://bit.ly/3cOEGHy', 'linda'),



        (203, 'Scene lights', 'White lightening scene lights', 10, '2x2', NULL, 'https://bit.ly/3cOEGHy', 'allen'),



        (204, 'Microphone', 'Mini clip on microphone', 20, '1 inch', NULL, 'https://bit.ly/3cOEGHy', 'kirsten');







-- INSERT OPERATION FOR belongs



INSERT INTO theater_wa_db.belongs (itemid, name)



VALUES  (200, 'marketing'),



        (201, 'hair'),



        (202, 'costume'),



        (203, 'scenography'),



        (204, 'scenography');







-- INSERT OPERATION FOR transaction



INSERT INTO theater_wa_db.transact (transid, name, description, amount, invoice, transactiondate, username, seasonid)



VALUES  (300, 'Buy Iphone 8', 'Buy Iphone 8 for members', 50000.00, 'https://bit.ly/2yIgCHD', '2020-04-26', 'lucas', 20192020),



        (301, 'Buy Hair Wax', 'Buy Hair Wax for hair department', 1000.00, 'https://bit.ly/2yIgCHD', '2020-03-05', 'linda', 20192020),



        (302, 'Buy Classic Dress', 'Buy Classic Dress for costume department', 400.00, 'https://bit.ly/2yIgCHD', '2020-04-15', 'linda', 20192020),



        (303, 'Buy Scene Lights', 'Buy Scene Lights for scenography department', 4000.00, 'https://bit.ly/2yIgCHD', '2020-04-25', 'allen', 20202021),



        (304, 'Buy Microphones', 'Buy Microphones for scenography department', 1000.00, 'https://bit.ly/2yIgCHD', '2020-03-09', 'kirsten', 20192020),



        (25 , 'TestTransaction', 'test transaction for 1 activity per transaction purpose', 500.00, 'https://ruzzante.eu', '2020-05-23', 'allen', 20192020);







-- INSERT OPERATION FOR acquires



INSERT INTO theater_wa_db.acquires (itemid, transid)



VALUES  (200, 300),



        (201, 301),



        (202, 302),



        (203, 303),



        (204, 304);







-- INSERT OPERATION FOR externalpartecipant



INSERT INTO theater_wa_db.externalpartecipant (partecipantid, name, surname, phonenumber, email)



VALUES  (100, 'andy', 'murray', 35147162789, 'andy@domain.com'),



        (101, 'roger', 'federer', 35182193219, 'roger@domain.com'),



        (102, 'carolina', 'smith', 35182193216, 'carolina@domain.com'),



        (103, 'sonia', 'martin', 351821932111, 'sonia@domain.com'),



        (104, 'alice', 'goulding', 35182193210, 'alice@domain.com');







-- INSERT OPERATION FOR activity



INSERT INTO theater_wa_db.activity (activityid, title, description, type, location, dateandtime, privacytag, maxaudience, audiencesize, seasonid)



VALUES  (50000002, 'Baruffe Cioxotte', 'Paris December 2020', 'Variety Show', 'Paris', '2020-12-28 21:36:45.566000', 'Public', 1000, 120, 20192020),



        (50000001, 'Medea', 'Padova May 2020', 'Rehearsal', 'Padova', '2020-05-26 20:17:24.485000', 'Public', 800, 100, 20192020),



        (50000003, 'Sior Todaro Brontolon', 'Padova October 2021', 'Variety Show', 'Padova', '2021-10-03 11:43:19.046000', 'Public', 1200, 150, 20202021),



        (50000004, 'La Locandiera', 'Hanoi October 2021', 'Play', 'Hanoi', '2021-10-29 19:30:19.046000', 'Public', 1000, 260, 20202021),



        (50000005, 'Video Promo stagione estiva 2021', 'Padova July 2021', 'Variety Show', 'Padova', '2021-07-20 18:30:19.046000', 'Private', 150, 100, 20202021);







-- INSERT OPERATION FOR attend



INSERT INTO theater_wa_db.attend (activityid, partecipantid)



VALUES  (50000001, 100),



        (50000003, 101),



        (50000002, 102),



        (50000004, 103),



        (50000005, 104);







-- INSERT OPERATION FOR generatedby



INSERT INTO theater_wa_db.generatedby (transid, activityid)



VALUES  (300, 50000001),



        (301, 50000003),



        (302, 50000003),



        (301, 50000004),



        (303, 50000005);











-- The following queries describe the insertion and update of data within the 'play' relation.



-- Following the steps as below:



-- 1. Firstly, a play is scheduled as an activity within the season.



-- 2. The relationship userrole is filled with the name of the member, the department he is part of, the playid he has a role into for a seasonid.



-- 3. A play is associated with one or more items that are needed for a specific play.



-- 4. The following steps are repeated.







-- INSERT OPERATIONS for play: Medea with the playid = 1







INSERT INTO theater_wa_db.play (playid, title, description, script, duration, posterimage, rehearsalschedule)



VALUES (500001, 'Medea', 'Play to be performed from 2020', 'Scene 1 - [Outside the home of Jason and Medea in Corinth. The Nurse, a slave who serves Medea, is standing by herself]



NURSE



 Oh how I wish that ship the Argo



 had never sailed off to the land of Colchis,



 past the Symplegades, those dark dancing rocks



 which smash boats sailing through the Hellespont.



 Then my mistress,



 Medea, never would''ve sailed away 10



 to the towers in the land of Iolcus,



 her heart passionately in love with Jason.



 She''d never have convinced those women,



 Pelias'' daughters, to kill their father.



 She''d not have come to live in Corinth here, [10]



 with her husband and her children—well loved



 in exile by those whose land she''d moved to.  ...', 90, 'https://i1.wp.com/i1223.photobucket.com/albums/dd515/flicheri/my%20album/my%20album/ROCK1/Medea_souvenir_1948_front-cover_zpsb7f9c3aa.jpg', '6 weeks');







-- INSERT OPERATIONS for schedule WITH playid = 1, activityid = 1



INSERT INTO theater_wa_db.schedule (playid, activityid)



VALUES (500001, 50000001);







-- INSERT OPERATIONS for uses



INSERT INTO theater_wa_db.uses (itemid, playid)



VALUES (200, 500001);







-- INSERT OPERATIONS for userrole



INSERT INTO theater_wa_db.userrole (username, seasonid, playid, name, role)



VALUES ('lucas', 20192020, 500001, 'marketing', 'Actor');







-- UPDATE OPERATIONS for play: Medea with the playid = 1



UPDATE theater_wa_db.play SET  description = 'Perform from 2020



                    \n Performed in Padova May 2020'



WHERE playid = 500001;







-- INSERT OPERATIONS for schedule WITH playid = 1, activityid=2



INSERT INTO theater_wa_db.schedule (playid, activityid)



VALUES (500001, 50000002);



-- INSERT OPERATIONS for uses



INSERT INTO theater_wa_db.uses (itemid, playid)



VALUES (201, 500001);







-- INSERT OPERATIONS for userrole



INSERT INTO theater_wa_db.userrole (username, seasonid, playid, name, role)



VALUES ('kirsten', 20192020, 500001, 'costume', 'Crew');







-- UPDATE OPERATIONS for play: Medea with the playid = 1



UPDATE theater_wa_db.play SET  description = 'Perform from 2020



                    \n Performed in Padova May 2020



                     \n Pending to Paris December 2020'







WHERE playid = 500001;







-- INSERT OPERATIONS for play: Romeo & Juliet with the playid = 2



INSERT INTO theater_wa_db.play (playid, title, description, script, duration, posterimage, rehearsalschedule) VALUES (500002, 'Romeo & Juliet', 'Perform until 2022', 'Scene 1 -SAMPSON



Enter Sampson and Gregory, with swords and bucklers,



of the house of Capulet.



Gregory, on my word we’ll not carry coals.



I strike quickly, being moved.



But thou art not quickly moved to strike.



A dog of the house of Montague moves me.



To move is to stir, and to be valiant is to



stand. Therefore if thou art moved thou runn’st



away.



A dog of that house shall move me to stand. I



will take the wall of any man or maid of Montague’s.



That shows thee a weak slave, for the weakest



goes to the wall.', 100, 'https://bit.ly/2VZADBC', '4 weeks');







-- INSERT OPERATIONS for schedule WITH playid = 2, activityid = 2



INSERT INTO theater_wa_db.schedule (playid, activityid)



VALUES (500002, 50000002);







-- INSERT OPERATIONS for uses



INSERT INTO theater_wa_db.uses (itemid, playid)



VALUES (200, 500002);







-- INSERT OPERATIONS for userrole



INSERT INTO theater_wa_db.userrole (username, seasonid, playid, name, role)



VALUES ('lucas', 20192020, 500002, 'marketing', 'Actor');







-- UPDATE OPERATIONS for play: Romeo & Juliet with the playid = 2



UPDATE theater_wa_db.play SET  description = 'Perform until 2022



                    \n Pending to Paris December 2020'



WHERE playid = 500002;







-- INSERT OPERATIONS for schedule WITH playid = 2, activityid = 32



INSERT INTO theater_wa_db.schedule (playid, activityid)



VALUES (500002, 50000003);







-- INSERT OPERATIONS for uses



INSERT INTO theater_wa_db.uses (itemid, playid)



VALUES (204, 500002);







-- INSERT OPERATIONS for userrole



INSERT INTO theater_wa_db.userrole (username, seasonid, playid, name, role)



VALUES ('linda', 20202021, 500002, 'hair', 'Crew'),



       ('kirsten', 20202021, 500002, 'costume', 'Crew'),



       ('allen', 20202021, 500002, 'scenography', 'Actor'),



       ('liam', 20202021, 500002, 'marketing', 'Actor');







-- UPDATE OPERATIONS for play: Romeo & Juliet with the playid = 2



UPDATE theater_wa_db.play SET  description = 'Perform until 2022



                    \n Pending to Paris December 2020



                    \n Pending to Hanoi March 2021'







WHERE playid = 500002;















