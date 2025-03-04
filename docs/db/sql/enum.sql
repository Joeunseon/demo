
-- DROP TYPE public.yn;
create type yn as enum('Y', 'N');



-- DROP TYPE public.menu;
create type menu as enum('MENU', 'PAGE', 'CREATE', 'READ', 'UPDATE', 'DELETE', 'TOOL');



-- DROP TYPE public.err_level;
create type err_level as enum('INFO', 'WARN', 'ERROR', 'CRITICAL');