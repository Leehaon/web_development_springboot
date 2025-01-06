SELECT * FROM users;
-- WHERE : 어떤 조건에 합치하는 것을 고를까(조건문과 유사)
-- 이전까지는 테이블 전체거나 혹은 특정 컬럼에 관련된 부분들을 조회했지만
-- 현업에서는 데이터의 일부 컬럼을 가져오거나 상위 n의 데이터를 조회하는 것
-- 뿐만 아니라 특정 컬럼의 값이 A인 데이터만 가져오는 등 복잡한 쿼리를 
-- 작성할 일이 있습니다.

-- 예제 : 회원 정보 테이블 users에서 거주 국가(country)가 한국(Korea)인
-- 회원ㅁ나 추출하는 쿼리문
SELECT * FROM users WHERE country = "Korea"

-- 거주 국가가 한국이 아닌 회원만 추출하는 쿼리문을 작성하시오.

SELECT * FROM users WHERE country != "Korea"

-- != : ~가 아닌

-- 거주 국가가 한국이면서 회원 아이디가 10인 회원만 추출
SELECT * 
	FROM users 
	WHERE country = "Korea" AND id = 10
	;	-- id가 PK이기 때문에
	
-- WHERE 절에서는 여러 조건을 동시에 적용할 수 있고, 조건의 개수에
-- 제한은 없음. 논리 연산 AND / OR / BETWEEN
	
-- 예제 : 회원 정보 테이블 users에서 가입 일시(created_at)가
	-- 2010-12-01부터 2011-01-01까지인 회원 정보를 출력해보세요.

SELECT *
	FROM users
	WHERE created_at BETWEEN "2010-12-10" AND "2011-01-01"
	;

-- where절을 작성하는데 있어서 컬럼여이 먼저나오고 =, !=, between등을 적용
-- select절 + from 테이블명 + where
-- between : 시작값과 종료 값을 '포함'하는 범위 내의 데이터를 조회
-- 			시간 값을 조회할 때는 [컬럼명] BETWEEN [시작날짜] AND [종료날짜]
--			마찬가지로 시작날짜와 종료날짜를 포함한 모든 것을 출력해준다.

-- 예제 : 가입일시가 2010-12-01부터 2011-01-01까지인 회원정보 출력
--	(BETWEEN을 사용하지 않고)

SELECT *
	FROM users
	WHERE created_at >= "2010-12-01" AND created_at <= "2011-01-01"
	;
-- 이상의 쿼리를 작성한 이유는 : BETWEEN A AND B구문이
-- [초과/미만]이 아니라 [이상/이하]임을 증명하기 위해서

-- 응용 : users에서 거주 국각가 KOREA, USA, UK인 회원 정보를 추출하세요.
SELECT *
	FROM users
	WHERE country = "Korea" 
	OR country = "USA"
	OR country = "UK"
	;

SELECT *
	FROM users
	WHERE country IN ("Korea", "USA", "UK")
	;
-- 응용 : users에서 거주국가가 Korea, USA, UK가 아닌 회원의 정보를 추출하세요.
SELECT *
	FROM users
	WHERE country != "Korea" 
	AND country != "USA"
	AND country != "UK"
	;
SELECT *
	FROM users
	WHERE country NOT IN ("Korea", "USA", "UK")
	;

-- LIKE : 해당 전치사 뒤의 작은따옴표/큰따옴표 내에서는 와일드카드를 사용 가능
--	SQL을 해성하는 컴퓨터가 LIKE 코드를 읽는 순간 와일드 카드를 감지하는데
--  SQL에서의 와일드 카드는 '%'로 0개 이상의 임의의 문자열을 의미하는 
--	메타문자(metacharacter)로 사용됨

-- 예제 users에서 country의 이름이 S로 시작하는 회원 정보 추출

SELECT *
	FROM users
	WHERE country LIKE "S%"
	;

-- 거주국가가 S로 시작하는 국가(ex)South Krea) 모두를 출력
-- users에서 country의 이름이 S로 끝나는 회원 정보 추출

SELECT *
	FROM users
	WHERE country LIKE "%S"
	;

-- 거주국가명에 S가 들어가기만 하면 다 출력하는 쿼리

SELECT *
	FROM users 
	WHERE country LIKE "%S%"
	;

-- 응용 : suers에서 country 이름이 S로 시작하지 않는 회원 정보만 추출

SELECT *
	FROM users
	WHERE country NOT LIKE "S%"
	;

-- IS : A IS B -> A는 B다라는 뜻이기 때문에
-- A 컬럼의 값이 B 이다 일 때 특히 null 일 때 '=' 대신 사용합니다.
-- 예제 : users에서 created_at 컬럼의 값이 null인 결과 출력
SELECT *
	FROM users
	WHERE created_at IS NULL;

SELECT *
	FROM users
	WHERE created_at IS NOT NULL;	-- 참고
	
-- 연습 문제
	-- 1. users에서 country가 Mexico인 회원 정보 추출.
		-- 단 creeated_at, phone, city, country 컬럼만 추출할 것
SELECT created_at, phone, city, country 
	FROM users
	WHERE country = "Mexico"
	;

	-- 2. products에서 id가 20 이하이고 price는 30 이상인 제품 정보 출력
		-- 단 기존 컬럼 전부 출력하고, 정상 가격에서 얼마나 할인 되었는지를
		-- (price - discount_price)
		-- discount_amount라는 컬럼명으로 추출할 것.
SELECT *,				-- 기존 컬럼 전부 출력
	(price - discount_price) AS "discount_amount"	-- 새로운 컬럼
	FROM products 
	WHERE id <= 20 AND price >= 30

	-- 3. users에서 country가 Korea, Canada, Belgium도 아닌 회원의
		-- 정보를 모두 출력할 것.
		-- 단, OR 연산자를 사용하지 않고 출력할 것.
SELECT *
	FROM users
	WHERE country NOT IN ("Korea", "Canada", "Belgium")
	;
	
	-- 4. products에서 name이 N으로 시작하는 제품 정보를 전부 출력할 것.
		-- 단, id, name, price 컬럼만 출력할 것
SELECT id, name, price
	FROM products
	WHERE name LIKE "N%"
	;
	
	-- 5. orders에서 order_daterk 2015-07-01부터 2015-10-31까지가
		-- 아닌 정보만 출력할 것.
SELECT *
	FROM orders
	WHERE order_date NOT BETWEEN "2015-07-01" AND "2015-10-31"
	;
	
-- 2.-- 기존 컬럼 전부 출력
--	(price - discount_price) AS "discount_amount"	-- 새로운 컬럼
-- 5. NOT BETWEEN 순인 것만 아시면 됩니다.
	
-- ORDER BY
-- 현재까지 WHERE을 이용해서 특정한 조건에 합치하는 데이터들을 조회하는 SQL문에 대해서
-- 학습했습니다.
	
-- 이상의 경우 저장된 순서대로 정렬된 결과만 볼 수 있습니다. - id라는 PK값에 따라
	
-- 이번에는 가져온 데이터를 원하는 순서대로 정렬하는 방법에 관한 것입니다.
	
-- 예제 : users에서 id를 기준으로 오름찬순 정렬 후 출력
SELECT *
	FROM users 
	ORDER BY id ASC; 		-- ASC : Ascending의 축약어로 '오름차순'
	
-- 예제 : user에서 id를 기준으로 내림차순 정렬 후 출력
SELECT *
	FROM users 
	ORDER BY id DESC;		-- DESC : Descending의 축약어로 '내림차순'
	
-- 이상에서 볼 수 있듯이 ORDER BY는 컬럼을 기준으로 행 데이터를 ASC 혹은 DESC로
-- 정렬할 때 사용 : 숫자의 경우는 쉽게 알 수 있지만, 문자의 경우는
-- 아스키 코드(ASCII Code)를 기준으로 합니다.

-- 예제 : users에서 city를 기준으로 내림차순 하여 전체 정보를 출력해보세요.
SELECT *
	FROM users 
    ORDER BY city DESC;

-- 예제 : users에서 created_at를 기준으로 오름차순 하여 전체 정보를 출력해보세요.
SELECT *
	FROM users 
    ORDER BY created_at ASC; 

-- 예재 : users에서 첫 번째 컬럼 기준으로 오름차순 정렬하여 출력
SELECT *
 	FROM users 
 	ORDER BY 1 ASC 
 	;

-- ORDER BY의 특징 : 컬럼명 대신에 컬럼 순서를 기준으로 하여 정렬이 가능.
-- 컬럼명을 몰라도 정렬할 수 있다는 장점이 있지만 주의할 필요가 있음

-- 예제 : users에서 username, phone, city, country, id 컬럼을 순서대로 출력
-- 하는데, 첫 번째 컬럼을 기준으로 오름차순 정렬 해보세요.
SELECT username, phone, city, country, id
	FROM users
	ORDER BY 1 ASC 
	;
-- 아까와 같이 ORDER BY 1 ASC로 했지만 정렬이 id가 아닌 username을 기준으로
-- ASCII Code가 적용되어 정렬 방식이 달라졌음을 확인 가능.
-- 즉, ORDER BY는 데이터 추출이 끝난 후를 기점으로 적용이 된다는 점을 명심해야 합니다.

-- 예제 : users에서 city, id 컬럼만 출력하는데, city 기준 내림차순
--	id 기준 오름차순으로 정렬
SELECT city, id
	FROM users 
	ORDER BY city DESC, id ASC
	;

-- 컬럼별로 다양하게 오름찬순 혹은 내림차순 적용이 가능

-- ORDER BY 정리
-- 1. 데이터를 가져올 때 지정된 컬럼을 기준으로 정렬함.
-- 2. 형식 : ORDER BY '컬럼명' ASC/DESC
-- 3. 2개 이상의 정렬 기준을 쉼표(,)를 기준으로 합쳐서 지정 가능
	-- 이상의 경우 먼저 지정된 컬럼이 우선하여 정렬됨.
-- 4. 2개 이상의 정렬 기준을 지정할 때 각각 지정 가능
	-- 이상의 경우 각 컬럼 당 명시적으로 ASC 혹은 DESC를 지정 해줘야 함.
	-- 하지만 지정하지 않을 경우 degault로 ASC 적용

-- 연습 문제
-- 1. products에서 price가 비싼 제품부터 순서대로 모든 컬럼 출력
SELECT *
	FROM products
	ORDER BY price DESC
	;

-- 2. orders에서 order_date 기준 최신순으로 모든 컬럼 출력
SELECT *
	FROM orders
	ORDER BY order_date ASC 
	;

-- 3. orderdetails에서 product_id를 기준 내림차순, 같은 제품 아이디 내에서는
-- quantity 기준 오름차순으로 모든 컬럼 출력

SELECT *
	FROM orderdetails 
	ORDER BY product_id DESC, quantity ASC -- (1)
	;

SELECT *
	FROM orderdetails 
	ORDER BY product_id DESC, quantity 	   -- (2)	
	;

-- ASC 적어도(1) 되고 안적어도(2) 되고

-- 여테까지 배운 것을 기준으로 실무에서는 어떤 방식으로 데이터 추출해서 사용하는지 예시
-- 실무에서는 select, where, order by를 사용해 다양한 데이터를 추출하는데,

-- 1. 배달 서비스 예시
	-- 1) 2023-08-01에 주문한 내역 중 쿠폰 할인이 적용된 내역 추출

SELECT *
	FROM 주문정보
	WHERE 주문일자 = '2023-08-01'
		AND 쿠폰할인금액 > 0
	;

	-- 2) 마포구에서 1인분 배달이 가능한 배달 음식점 추출

SELECT *
	FROM 음식점정보
	WHERE 지역 = "마포구"
		AND 1인분가능여부 = 1;		-- 1을 쓸 경우에는 True의미 / 0은 False 의미
