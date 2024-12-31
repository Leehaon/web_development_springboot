-- 주석 처리 하는 방법
-- 1. SELECT : 보여달라 / 조회해라
SELECT "Hello, SQL!";
-- select : 보여달라 / Hello, SQL!이라는 str
SELECT 12 + 7; -- SQL문을 통한 연산이 가능

-- 결과 창의 첫 행에 각각 Find, Insight, with SQL을 3 개의 칸에
-- 걸쳐 순서대로 출력하는데, 컬럼명을 순서대로 First, Second, Third로 출력
SELECT 
	"Find" as 'First',
	"Insight" as 'Second',
	"whith SQL" as 'Third';
-- 이런 식으로 작성하는 이유는 모두가 사람이 알아보기 쉽게 하기 위해서이다.
-- as : alias 알리아스라고 발음하는데, 데이터가 들어가는 부분에 대해서 컬럼에 댛나 별칭을
-- 붙일 때 사용하는 구문 
-- 쉼표 (,)의 역할 : 나열 할 때 사용
-- SQL문의 특징 : 큰따움표, 작은따움표, 따움표없음을 구분하지 않음

-- 연습 문제
-- 1. SELECT를 이용하여 28 + 891 의 결과를 표시하라.
-- 2. SELECT를 이용하여 19 x 27의 결과를 표시하라. 단, 컬럼명은 Result로 표시하라.
-- 3. SELECT를 이용하여 다음 세 가지 결과를 각각 다른 컬럼으로 결과 창에 표시하라
-- 37 + 172 (단, 컬럼명은 Plus)
-- 25 x 78 (컬럼명은 Times)
-- I love SQL(컬럼명 Result) 
SELECT 28 + 891;
SELECT 
	19 * 27 as "Result";
SELECT 
	37 + 172 as "Plus",
	25 * 78 as "Times",
	"I love SQL" as "Result";

-- 2. FROM : ~로 부터 + 테이블명 
-- FROM은 데이터가 저장된 위치를 나타냄
SELECT *
	FROM users;
-- users.csv 파일에 있었던 모든 테이블과 컬럼과 값이 추력이 됐음을확인 가능
-- * : asterisk == all : 와일드카드라느 표시 // java
-- select * from users; : users 테이블에 있는 모든 컬럼의 값을 조회하라

-- 연습문제 2 : 제품 정보 테이블 products에 있는 모든 데이터를 출력하시오.
select * from products;
-- 한 줄로 쓰는 것도 가능 -> 긴 쿼리문을 쓸 때는 가독성 때문에 개행을 하게 되는데,
-- 차근차근 연습하기 위해서 한 줄로 쓸 때도 있고 나눌 때도 있을 예정
-- 원래 제가 쿼리문 명령어에 해당하는 부분은 가독성 때문에 대문자를 쓸 예정이었는데
-- 얘가 지혼자서 소문자로 바뀌어가지고 미리 설명드리는데
-- 얘는 대소문자를 구분하지 않습니다.
-- LIMIT : 개수 제한을 거는 명령어(IDE를 쓰느냐에 따라서 TOP일 때도 있습니다)
select * FROM products LIMIT 3;
-- 이렇게 *를 사용해서 전체 정보를 조회하는 것을 full scan을 쓰는데, 빈도가 높지 않습니다
-- 물론 시험에도 자주 나오지 않습니다.

-- 개수 제한을 걸기 위한 LIMIT와 특정 컬럼을 조회하는 행터로 수업을 하도록 하겠습니다.

-- SELECT 컬럼명1, 컬럼명2 from 테이블명;
-- 제품 정보 테이블인 products에서 제품 아이디(id), 제품명(name) 컬럼만 출력합니다.
select id, name FROM products;
-- products에서 가격(price), 할인가(discount_price) 컬럼을 10개만 출력합니다.
select price, discount_price 
	FROM products 
	LIMIT 10;
-- SQL문의 경우에는 순서가 매우 명확하게 정해져있는 편이기 때문에
-- 읽기는 쉽지만 익숙해지기 전까지 직접 작성하는 것이 까다로운 편

-- orderdetails의 모든 정보 표시하세요
SELECT * FROM orderdetails;
-- 회원 정보 테이블 users에서 상위 7건만 표시하세요.(전부 다요)
SELECT * FROM users LIMIT 7;
-- orders에서 id, user_id, order_date 컬럼의 데이터를 모두 표시하세요.
SELECT id, user_id, order_date FROM orders;