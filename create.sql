SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS Question;
CREATE TABLE Question(
    QuestionID INTEGER AUTO_INCREMENT, 
    QuestionType INTEGER,
    PRIMARY KEY (QuestionID)
);

DROP TABLE IF EXISTS Answers;
CREATE TABLE Answers(
    QuestionID INTEGER,
    CorrectAnswer VARCHAR(500),
    PRIMARY KEY (QuestionID, CorrectAnswer),
    FOREIGN KEY (QuestionID) REFERENCES Question(QuestionID)
);

DROP TABLE IF EXISTS MultipleChoice;
CREATE TABLE MultipleChoice(
    QuestionID INTEGER,
    Options VARCHAR(500),
    PRIMARY KEY (QuestionID, Options),
    FOREIGN KEY (QuestionID) REFERENCES Question(QuestionID)
);

DROP TABLE IF EXISTS Tags;
CREATE TABLE Tags(
    QuizID INTEGER,
    Tag VARCHAR(500),
    PRIMARY KEY (QuizID, Tag),
    FOREIGN KEY (QuizID) REFERENCES Quiz(QuizID)
);

DROP TABLE IF EXISTS QuestionResponse;
CREATE TABLE QuestionResponse(
    QuestionID INTEGER,
    QuestionText VARCHAR(2000),
    PRIMARY KEY (QuestionID),
    FOREIGN KEY (QuestionID) REFERENCES Question(QuestionID)
);

DROP TABLE IF EXISTS FillInBlank;
CREATE TABLE FillInBlank(
    QuestionID INTEGER,
    QuestionTextBefore VARCHAR(2000),
    QuestionTextAfter VARCHAR(2000),
    PRIMARY KEY (QuestionID),
    FOREIGN KEY (QuestionID) REFERENCES Question(QuestionID)
);

DROP TABLE IF EXISTS PictureResponse;
CREATE TABLE PictureResponse(
    QuestionID INTEGER,
    ImageFile VARCHAR(500),
    PRIMARY KEY (QuestionID, ImageFile),
    FOREIGN KEY (QuestionID) REFERENCES Question(QuestionID)
);

DROP TABLE IF EXISTS Users;
CREATE TABLE Users(
    Username VARCHAR(100),
    Password VARCHAR(100),
    Admin BIT,
    PRIMARY KEY (Username)
);

DROP TABLE IF EXISTS Notifications;
CREATE TABLE Notifications(
    NotificationID INTEGER AUTO_INCREMENT,
    Checked BIT,
    NotificationType INTEGER,
    PRIMARY KEY (NotificationID)
);

DROP TABLE IF EXISTS Notes;
CREATE TABLE Notes(
    NotificationID INTEGER,
    Sender VARCHAR(100),
    Recipient VARCHAR(100),
    Note VARCHAR(2000),
    Checked BIT,
    PRIMARY KEY (NotificationID),
    FOREIGN KEY (NotificationID) REFERENCES Notifications(NotificationID)
);


DROP TABLE IF EXISTS Friends;
CREATE TABLE Friends(
    Sender VARCHAR(100),
    Recipient VARCHAR(100),
    Status INTEGER,
    PRIMARY KEY (Sender, Recipient)
);

DROP TABLE IF EXISTS Challenge;
CREATE TABLE Challenge(
    NotificationID INTEGER,
    Sender VARCHAR(100),
    Recipient VARCHAR(100),
    QuizID INTEGER,
    Link VARCHAR(2000),
    Score DOUBLE,
    Checked BIT,
    PRIMARY KEY (NotificationID),
    FOREIGN KEY (NotificationID) REFERENCES Notifications(NotificationID)
);

DROP TABLE IF EXISTS Achievements;
CREATE TABLE Achievements(
    Username VARCHAR(100),
    Achievement VARCHAR(500),
    PRIMARY KEY (Username, Achievement)
);

DROP TABLE IF EXISTS Quiz;
CREATE TABLE Quiz(
    QuizID INTEGER AUTO_INCREMENT,
    QuizName VARCHAR(2000),
    Description VARCHAR(2000),
    PRIMARY KEY (QuizID)
);

DROP TABLE IF EXISTS QuizzesTaken;
CREATE TABLE QuizzesTaken(
    Username VARCHAR(100),
    QuizID INTEGER,
    Score DOUBLE,
    StartTime DATETIME,
    EndTime DATETIME,
    PRIMARY KEY (Username, QuizID, StartTime),
    FOREIGN KEY (QuizID) REFERENCES Quiz(QuizID),
    FOREIGN KEY (Username) REFERENCES Users(Username)
);

DROP TABLE IF EXISTS QuizzesMade;
CREATE TABLE QuizzesMade(
    Username VARCHAR(100),
    QuizID INTEGER,
    Status INTEGER,
    Time DATETIME,
    PRIMARY KEY (Username, QuizID),
    FOREIGN KEY (Username) REFERENCES Users(Username),
    FOREIGN KEY (QuizID) REFERENCES Quiz(QuizID)
);


DROP TABLE IF EXISTS QuizQuestions;
CREATE TABLE QuizQuestions(
    QuizID INTEGER,
    QuestionID INTEGER,
    PRIMARY KEY (QuizID, QuestionID),
    FOREIGN KEY (QuizID) REFERENCES Quiz(QuizID),
    FOREIGN KEY (QuestionID) REFERENCES Question(QuestionID)
);

DROP TABLE IF EXISTS Announcement;
CREATE TABLE Announcement(
    AnnouncementID INTEGER AUTO_INCREMENT,
    Text VARCHAR(2000),
    PRIMARY KEY (AnnouncementID)
);

SET FOREIGN_KEY_CHECKS=1;
