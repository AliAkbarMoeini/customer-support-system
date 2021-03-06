BEGIN;


CREATE TABLE public.query
(
    id                 bigint                      NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    creation_date_time timestamp without time zone NOT NULL,
    is_open            boolean                     NOT NULL,
    title              character varying(100)      NOT NULL,
    user_id            bigint                      NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE public.reply
(
    id                bigint                      NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    date_time         timestamp without time zone NOT NULL,
    is_from_supporter boolean                     NOT NULL,
    message           text                        NOT NULL,
    query_id          bigint,
    PRIMARY KEY (id)
);

CREATE TABLE public.role
(
    id   integer               NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    name character varying(20) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE public.users
(
    id         bigint                 NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    email      character varying(50),
    first_name character varying(40),
    last_name  character varying(40),
    password   character varying(255) NOT NULL,
    username   character varying(40)  NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE public.users_roles
(
    user_id  bigint  NOT NULL,
    roles_id integer NOT NULL,
    PRIMARY KEY (user_id, roles_id)
);

ALTER TABLE public.query
    ADD FOREIGN KEY (user_id)
        REFERENCES public.users (id)
        NOT VALID;


ALTER TABLE public.reply
    ADD FOREIGN KEY (query_id)
        REFERENCES public.query (id)
        NOT VALID;


ALTER TABLE public.users_roles
    ADD FOREIGN KEY (roles_id)
        REFERENCES public.role (id)
        NOT VALID;


ALTER TABLE public.users_roles
    ADD FOREIGN KEY (user_id)
        REFERENCES public.users (id)
        NOT VALID;

END;