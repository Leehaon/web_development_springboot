-- UNION
-- 1. 컬럼 목록이 같은 데이터를 위아래로 결합
--	데이터를 위 아래로 수직 결합해주는 기능 -> 컬럼의 형식과 개수가 같은 두 데이터 결과 집합을
-- 하나로 결합.

--    JOIN의 경우에는 여러 가지 조건을 미리 충족시켜줘야만 합니다 -> ON

(SELECT * FROM users) UNION (SELECT * FROM users);

-- 이상의 쿼리의 문제점은 결합 기능을 지닌 UNION의 결과값이 select * from users;와
-- 동일하다는 점입니다.

-- 해당 이유는 : UNION은 결합하는 두 결과 집합에 대한 '중복 제거 기능'이 포함돼있습니다.

-- 중복을 제거하지 않고 출력하는 명령어 : UNION ALL

(SELECT * FROM users) UNION ALL (SELECT * FROM users)
	ORDER BY id;

-- UNION은 중복 제거 가능 / UNION ALL은 중복 포함 출력

-- 참고 UNION ALL 사용시 SELECT에서 컬럼 선별 예시
(SELECT id, phone, city, country		-- 일부 컬럼만 지정해서 출력
	FROM users)
UNION ALL
(SELECT id, phone, city, country		-- 기준이 되는 첫번째 select절에서 선택하는
	FROM users)							-- 컬럼의 종류 및 개수가 완벽히 일치해야 합니다.
	ORDER BY id;

-- 실무에서는 UNION ALL이 더 권장됩니다. UNION의 경우 중복 제거가 있는데, 대량의 데이터를 대상으로
-- 중복 제거할 때 컴퓨터에 무리한 연산 부하를 줄 가능성이 있기 때문에
-- 일단 UNION ALL을 통해서 최종 결과 형태 확인 후에 -> UNION을 적용하는 식으로
-- 프로세스가 짜여있는 편

-- users에서 country가 Korea인 회원 정보만 추출하고(1번 추출), 
-- Mexico인 회원 정보만 추출해서(2번추출) 결합해보자.
-- (단, 컬럼은 id, phone, city, country만 출력하고, 최종 결과 집합은 country 기준 알파벳 순)

-- 이렇게 작성하지 마세요
SELECT id, phone, city, country FROM users WHERE country IN ("Korea", "Mexico");

(SELECT id, phone, city, country FROM users WHERE country = "Korea")
UNION ALL 
(SELECT id, phone, city, country FROM users WHERE country = "Mexico")
ORDER BY country
;

-- 연습 문제
-- 1. orders에서 order_date가 2015년 10월 건과 2015년 12월인 건을 select로 각각 추출하고,
-- 두 결과 집합을 UNION ALL을 사용해 하나로 결합하세요(단, 최종 결과는 최신순으로 정렬)
-- 1번 쿼리
(SELECT * FROM orders WHERE SUBSTR(order_date, 1, 7) = "2015-10")
UNION ALL 
(SELECT * FROM orders WHERE SUBSTR(order_date, 1, 7) = "2015-12")
ORDER BY order_date DESC;
-- 2번 쿼리
(SELECT * FROM orders 
	WHERE order_date >= "2015-10-01" AND order_date < "2015-11-01")
UNION ALL 
(SELECT * FROM orders 
	WHERE order_date >= "2015-12-01" AND order_date < "2016-01-01")
ORDER BY order_date DESC;

-- SQL상에서의 문자열 비교 방식
-- 문자열을 왼쪽에서 오른쪽으로 한 문자씩 비교
-- ASCII / 유니코드 값을 기준으로 비교합니다.
-- 왼쪽부터 읽어오다가 다른 문자가 발견되는 순간에 그 값에 따라 크고 작음을 판별합니다.

-- "2015-10-01" vs "2015-11-01"의 경우,
-- "2" == "2" / "0" == "0" / "1" == "1" / "5" == "5" / "-" == "-" / "1" == "1"까지 동일
-- 그 다음 순간에 "0" != "1"이 다른 시점에 들어갔을 때 크기 비교가 이루어집니다.

-- YYYY-MM-DD 형식으로 지정돼있다면, 문자열 비교 결과와 실제 날짜 비교 결과가 동일하게 작용함.
-- 그래서 MM-DD-YYYY 형태로 돼있다면 오류가 발생할 가능성이 있습니다.










-- 2. users에서 USA에 거주 중이면서 마케팅 수신에 동의(1)한 회원 정보와 France에 거주 중이면서
-- 마케팅 수신에 동의하지 않은(0) 회원 정보를 SELECT로 각각 추출하고, 두 결과 집합을 UNION ALL을
-- 사용해 하나로 결합하라(단, 최종 결과는 id, phone, country, city, is_marketing_agree 
-- 컬럼 추출하고, 거주 국가 기준으로 알파벳 역순으로 추출하세요.)

(SELECT id, phone, country, city, is_marketing_agree 
	FROM users 
	WHERE is_marketing_agree = 1 
	AND country = "USA")
UNION ALL
(SELECT id, phone, country, city, is_marketing_agree 
	FROM users 
	WHERE is_marketing_agree = 0 
	AND country = "France")
ORDER BY country DESC;

-- 3. 이건 풀지마세요. 문제만 적어놓고 같이 풀겠습니다.
--		UNION을 활용하여 orderdetails와 products를 FULL OUTER JOINM 조건으로 결합하여 출력
-- 		하세요. -> 굳이 이런 형식으로 시험 문제 및 실무에까지 이용하는 경우는 거의 없습니다.
(SELECT * FROM orderdetails o LEFT JOIN products p ON o.product_id = p.id)
UNION
(SELECT * FROM orderdetails o RIGHT JOIN products p ON o.product_id = p.id)
;

-- 서브 쿼리
-- SQL 쿼리 결과를 테이블처럼 사용하는, 쿼리 속의 쿼리
-- 서브 쿼리는 작성한 쿼리를 소괄호로 감싸서 사용하는데, 실제로 테이블은 아니지만
-- 테이블처럼 사용이 가능합니다.

-- products에서 name(제품명)과 price(정상가격)을 모두 불러오고, '평균 정상 가격을
-- 새로운 컬럼'으로 각 행마다 출력해보세요.

SELECT name, price, (SELECT AVG(price) FROM products) FROM products;
-- SELECT AVG(price) FROM products;를 하는 경우 전체 price / 행의 개수로 나눈 데이터가
-- 단 하나이므로 SELECT name, price, AVG(price) FROM products; 로 작성하면
-- 1행짜리만 도출됨.

-- 이를 막기 위해서 서브쿼리를 적용했습니다.

-- products 테이블의 name / price를 불러오는 것은 기본적인 select절입니다.
-- 그런데 select 절에는 단일값을 반환하는 서브 쿼리가 올 수 있습니다.

-- 스칼라(Scalar) 서브 쿼리 : 쿼리의 결과가 단일 값을 반환하는 서브 쿼리

SELECT name, price, 38.5455 AS avgPrc FROM products;

-- 특정한 단일 결과값을 각 행에 적용을 하고 싶다면 이상과 같은 하드코딩이 가능합니다.
-- 하지만 정확한 값을 얻기 위해서 사전에 쿼리문으로 
-- SELECT AVG(price) FROM products; 가 요구된다는 점에서 효율적이지는 않고,
-- 실무 상황에서 실제로 전제 쿼리문을 실행시킨 이후에 확인해야 해서
-- 서브 쿼리를 작성하는 편이 권장됩니다.

-- 스칼라 서브 쿼리를 작성할 때 '단일 값'이 반환되도록 작성해야 한다는 점에 유의하세요.
-- 만약 2개 이상의 집계 값을 기존 테이블에 추가하여 출력하고 싶다면 스칼라 서브 쿼리르 따로
-- 나누어서 작성해야 합니다.

-- users에서 city별 회원 수를 카운트하고, 회원 수가 3명 이상인 도시 명과 회원 수를 출력해보자.
-- (단, 회원 수를 기준으로 내림차순)

SELECT city, COUNT(DISTINCT id) FROM users GROUP BY city; -- > 도시 별로
														  -- id 개수를 계산했습니다.
-- 1번 쿼리
SELECT city, COUNT(DISTINCT id)
	FROM users 
	GROUP BY city
	HAVING COUNT(DISTINCT id) >= 3
	ORDER BY COUNT(DISTINCT id) DESC;


-- orders와 staff를 활용해 last_name 이 Kyle이나 Scott인 직원의 담당 주문을 출력하려면?
-- (단, 서브쿼리 형태를 활용하자)


SELECT id 
	FROM staff 
	WHERE last_name IN ("Kyle", "Scott");					-- 조건절에 쓰일 경우에 
													-- scalar 서브가 아니었다는점에 주목

-- 이상의 코드는 staff 테이블에서 id 값이 3, 5를 도출해냈습니다.
-- 해당 결과를 가지고 orders 테이블에 적용하는 형태로 작성합니다.

SELECT *
	FROM orders
	WHERE staff_id IN (
		SELECT id 
			FROM staff 
			WHERE last_name IN ("Kyle", "Scott")
	)
	ORDER BY staff_id
	;


-- WHERE 절 내에서 필터링 조건 지정을 위해 중첩된 서브 쿼리를 작성 가능
-- WHERE 에서 IN 연산자와 함께 서브 쿼리를 활용할 경우 :
-- 	컬럼 개수와 필터링 적용 대상 컬럼의 개수가 '일치'해야만 합니다.

-- 이상의 코드에서 서브쿼리의 결과 도출되는 컬럼의 숫자 = 1 (staff테이블의 id) / 행 = 2

SELECT *
	FROM orders
	WHERE (staff_id, user_id) IN (		-- 필터링 대상 컬럼 개수 = 2
		SELECT id, user_id				-- 서브쿼리 컬럼 개수 = 2
			FROM staff 
			WHERE last_name IN ("Kyle", "Scott")
	)
	ORDER BY staff_id
	;

-- 결과값으로 직원 정보 테이블에 존재하는 id, user_id와 동일한 값이 orders 테이블의
-- staff_id, user_id 컬럼에 있을 경우 반환하여 출력합니다.
-- 이상의 쿼리문의 해석 -> 직원 자신이 자기 쇼핑몰에서 주문한 이력이 반환된 것.

-- products에서 discount_price가 가장 비싼 제품 정보 출력하세요.
-- (단, products의 전체 컬럼이 다 출력되어야 합니다)

SELECT MAX(discount_price) FROM products;

SELECT *
	FROM products
	WHERE discount_price IN (
		SELECT MAX(discount_price) 
			FROM products
	);


-- orders에서 주문 월(order_date 컬럼 활용)이 2015년 7월인 주문 정보를,
-- 주문 상세 정보 테이블 orderdetails에서 quantity가 50 이상인 정보를 각각 서브 쿼리로 작성하고,
-- INNER JOIN하여 출력해봅시다.
	
-- 1)
SELECT *
	FROM orders
		WHERE order_date >= "2015-07-01"
			AND order_date < "2015-08-01";

-- 2)
SELECT *
	FROM orderdetails
		WHERE quantity >= 50;	

-- 1)과 2)의 INNER JOIN 구문 -> from절에서 이루어집니다.

SELECT *
	FROM (SELECT *
			FROM orders
			WHERE order_date >= "2015-07-01"
				AND order_date < "2015-08-01") o-- 1)의 결과가 테이블이었기 때문에 별칭 o를 표기
		 INNER JOIN 
		 (SELECT *
			FROM orderdetails
			WHERE quantity >= 50) od
		ON o.id = od.order_id
		;

-- 서브 쿼리를 작성하기 위한 방안 중에 하나는 서브 쿼리에 들어가게 될 쿼리문을 작성한 결과값을 확인.
-- 이후 해당 쿼리가 scalar냐 아니냐에 따라서 그 위치 역시 어느 정도 통제 가능
-- ex) scalar인 경우에는 select절에 들어가는 것처럼
-- 이상의 경우에는 결과값이 테이블 형태로 나왔기 때문에 이를 기준으로 INNER JOIN했습니다.

-- 서브 쿼리 정리하기
-- 쿼리 결과값을 메인 쿼리에서 값이나 조건으로 사용하고 싶을 때 사용

-- SELECT / FROM / WHERE 등 사용 위치에 따라 불리는 이름이 다르다.

-- 정리 1. SELECT 절에서의 사용
-- 형태
-- SELECT ..., ([서브쿼리]) AS [컬럼명]
-- ...이하 생략

-- SELECT에서는 '단일 집계 값'을 신규 컬럼으로 추가하기 위해 서브 쿼리를 사용.
-- 여러 개의 컬럼을 추가하고 싶을 때는 서브 쿼리를 여러 개 작성하면 됩니다.
-- 특징 : SELECT의 서브 쿼리는 메인 쿼리의 FROM에서 사용된 테이블이 아닌 테이블도 사용이 가능하기
-- 	때문에 불필요한 조인 수행을 줄일 수 있다는 장점이 있다.

-- 정리 2. FROM에서 사용
-- 형태 :
-- SELECT ...
--	FROM ([서브쿼리]) a
-- ...

-- FROM에서 사용되는 서브 쿼리 : '인라인 뷰', 마치 테이블처럼 서브 쿼리의 결과값을 사용 가능.
-- 또한 FROM에서 2개 이상의 서브 쿼리를 활용하여 JOIN 연산 가능
-- 이 때 조인 연산을 위해 별칭 생성 가능한데 서브 쿼리가 끝나는 괄호 뒤에 공백을 한 칸 주고
-- 원하는 별칭을 쓰면 된다(orders o / orderdetails od와 같은 방식)

-- 특징 :
--	FROM에서 서브 쿼리를 적절히 활용하면 적은 연산으로 같은 결과를 도출 가능. 단, RDBS 기준
--	테이블 검색을 빠르게 할 수 있는 인덱스* 개념이 있는데 서브 쿼리를 활용하면 인덱스를 사용하지 못하는
--  경우가 있으므로 주의해야 함. 

-- 인덱스(index) : 테이블의 검색 속도를 높이는 기능으로, 컬럼 값을 정렬하여 검색시 더욱 빠르게
-- 	찾아내도록 하는 자료 구조

-- 정리 3. WHERE에서의 사용

-- 형태 :
-- ...
-- WHERE [컬럼명] [조건 연산자] ([서브 쿼리])
-- ...

-- WHERE에서 필터링을 위한 조건 값을 설정하는데 서브 쿼리 사용 가능.
-- 위의 예시에서는 IN 연산자를 사용했지만, 다른 비교 연산자도 사용 가능.

-- 특징 : IN 연산자의 경우에 다중 컬럼 비교를 할 때는 서브 쿼리에서 추출하는 컬럼의 개수와
--	WHERE에 작성하는 필터링 대상 컬럼 개수가 일치 해야 함. -> 이 때 필터링 대상 컬럼이 2 개 이상이면
-- ()로 묶어서 작성해야 합니다.

-- 1. 데이터 그룹화하기(GROUP BY + 집계함수)
-- 2. 데이터 결과 집합 결합하기(JOIN + 서브쿼리)
-- 3. 테이블 결합 후 그룹화하기(JOIN + GROUP BY)
-- 4. 서브 쿼리로 필터링(WHERE절 + 서브쿼리)
-- 5. 같은 행동 반복 대상 추출(LEFT JOIN)		

-- 1. users에서 created_at 컬럼 활용하여 연도별 가입 회원 수를 추출

SELECT *
FROM users;

SELECT substr(created_at, 1, 4), COUNT(DISTINCT id)
	FROM users
	WHERE created_at IS NOT null
	GROUP BY substr(created_at, 1, 4);

-- 2. users에서 country, city, is_auth 컬럼을 활용, 국가별, 도시별로 본인 인증한 회원 수를
-- 추출하라.
SELECT country, city, SUM(is_auth)		-- is_auth가 1이면 본인인증한거니까 is_auth들의 합이 10이라면
										-- 본인인증한 회원 숫자가 10이라는 뜻이라서
FROM users
GROUP BY country, CITY
ORDER BY SUM(is_auth) DESC;