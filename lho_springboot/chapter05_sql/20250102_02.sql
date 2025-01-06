-- 데이터를 그룹화하고, 함수로 계산하는 방법 학습 예정.
-- 그룹화(grouping)는 조건에 따라 데이터를 그룹으로 묶는 것으로(WHERE과는 차이가 있음),
-- 데이터를 그룹화하면 함수로 원하는 계산이 가능

-- 예를 들어 실습 데이터에서 회원 수를 구하려면 어떡해야할까, 전체 회원 수가 아니라,
-- 국가별 회원 수를 구하는 방법, 제품별 매출을 구하는 방법 등을 실제로 알아볼 수 있음.

-- SQL내에서 사용하는 함수들은 주로 평균, 개수, 합계 등을 구하는 '집계 함수',
-- 문자열을 원하는 만큼 잘라내거나 대/소문자를 변경하는 등의 기능을 수행하는 '일반 함수'
-- 등으로 나눌 수 있다.

-- 함수를 적용하기 위한 전제 조건 : GROUP BY
-- 전체 데이터를 째로 함수 적용하는 경우도 있지만, 그룹별로 수치를 도출하는 일도 있는데,
-- 이럴 경우 사용하는 명령어가 GROUP BY
-- 이를 이용해 데이터를 그룹으로 묶은 후 필요 함수 적용 가능
-- 예를 들어 '국가별 회원 수를 구하라' 혹은 '일별 매출 계산하라'와 같은
-- 그룹화 기준을 지정해서 원하는 계산을 수행할 수 있음.

-- GROUP BY로 계산한 결과를 필터링하는 HAVING 명령어
-- GROUP BY를 적용해서 '국가별 회원 수를 구하고' 이후에 '그리고 회원 수가 10명 이상인'
-- '국가만 도출하라'는 등 집계 함수로 계산한 결과 중 조건에 맞는 데이터만 필요한 경우에는
-- WHERE을 쓰는 것이 아니라 'HAVING'을 사용합니다.

-- 집계 함수 예시
-- 예제 : users에서 모든 행 수를 세어봅니다 : COUNT
SELECT *
	FROM users
	;

SELECT COUNT(*)					-- COUNT(컬럼명, 사실 *) 집계함수에 해당
	FROM users					-- 대상 테이블 전체 행의 개수를 세는 역할
	;

-- users에서 존재하는 country 컬럼의 데이터 개수
SELECT COUNT(country)	-- country 컬럼에서 null이 아닌 값의 개수, 즉 중복 계산
	FROM users
	;

SELECT *
	FROM users
	WHERE country IS NULL
	; 

-- country의 데이터값의 종류를 계산하고 싶다면 어떡해야할까?
SELECT COUNT(DISTINCT country)		-- 중복을 제거하기 위해서는 distinct
	FROM users
	;

-- DISTINCT : 중복 값을 제거하여 고유한 값만 반환하는 키워드/ SELECT와 함께 사용
SELECT COUNT(1)			-- COUNT(컬럼숫자) 가능
	FROM users
	;

-- MIN / MAX / COUNT / SUM / AVG
-- products에서 최저가를 구하시오(price 컬럼 이용)
SELECT MIN(price) FROM products;

-- products에서 최대가를 구하시오(price 컬럼 이용)
SELECT MAX(price) FROM products;

-- products에서 전체 데이터 가격의 합 구하시오(price 컬럼 이용)
SELECT SUM(price) FROM products;

-- products에서 price의 평균(단, 결과값의 컬럼명을 avgPrice로 변경)
SELECT ROUND(AVG(price), 2) AS "avgPrice" FROM products;
-- ROUND(집계함수결과, 소수점몇째자리까지인지)

집계 함수 예시
SUM : 합계 : SUM(컬럼명)
AVG : 평균 : AVG(컬럼명)
MIN : 최소값 : MIN(컬럼명)
MAX : 최대값 : MAX(컬럼명)
COUNT : 개수 : COUNT(컬럼명)

일반 함수 예시
ROUND(컬럼명, 소수점자리수) : 소수점자리를 지정한 자릿수까지 반올림하여 반환
SUBSTR(컬럼명, 시작위치, 가져올문자개수) : 문자열을 지정한 시작 위치부터 지정한 문자 개수만큼 추출
LENGTH(컬럼명) : 문자열의 길이 반환
UPPER(컬럼명) : 알파벳 문자열을 대문자로 반환
LOWER(컬럼명) : 알파벳 문자열을 소문자로 반환

집계 함수는 여러 행의 데이터를 '하나의 결과값'으로 집계하는 반면, 일반 함수는
한 행의 데이터에 하나의 결과값을 반환한다.
이상을 이유로 집계 함수는 SELECT에서만 사용 가능
일반 함수는 SELECT뿐만 아니라 WHERE에서도 사용 가능.
이상의 함수 예시들은 전부 다가 아니라 일부이므로 필요시마다 소개하도록 하겠습니다.

GROUP BY
	-- 어떤 기준으로 묶어서 계산하느냐와 관련있는 키워드로,
-- 집계 함수만으로 원하는 결과를 얻을 수 없을 때(데이터 전체가 아니라, 원하는 기준으로
-- 그룹을 나눈 상태에서 계산을 해야할 때를 의미)가 있습니다.
-- 예를 들어, 전체 회원 수가 아닌 국가별 회원 수를 나누어 계산하기도 하며,
-- 월별로 가입한 회원 수를 집계하기도 하기 때문.
-- 즉, 특정 조건으로 나눈 그룹별 계산을 수행할 때는 먼저 그룹화를 선행해야 합니다.
-- 그 후 집계 함수는 특정 컬럼을 기준으로 데이터를 그룹화 한 후에, 그룹별로 집계 함수를
-- 적용해야 합니다.
-- 예제 : users에서 country가 Korea인 회원 수를 출력
SELECT COUNT(id)
	FROM users
	WHERE country = "Korea"
	;

-- 이상의 경우에 country = Korea인 경우는 출력할 수 있는데,
SELECT count(DISTINCT country)
	FROM users
	;

-- 를 확인해보면 count(DISTINCT country)의 결과값이 17이므로
-- 모든 국가를 대상으로 회원 수를 확인해보기 위해서는 국가별로 즉, 총 17번의
-- SQL문을 작성해야 합니다.
-- 국가 수는 적지도 않고, 실제 환경에서는 훨씬 더 많은 데이터를 다루는데, 조건을 일일이
-- 적용하기에는 비효율적입니다.
-- 즉, where절에서 country =의 값을 매번 바꾸는 방식은 사용하지 않습니다.

-- 다음 시간에 저희는 GROUP BY를 통해 어떻게 묶을지 학습하도록 하겠습니다.