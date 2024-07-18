candidate table-:
    create table candidate(
        cid number(3),
        name varchar(40),
        roll number(3),
        email varchar(30),
        correct number(3),
        password varchar(100))




question table-:
    create table question(
        qid number(2),
        question varchar(200),
        op1 varchar(200),
        op2 varchar(200),
        op3 varchar(200),
        op4 varchar(200),
        ans varchar(200))
