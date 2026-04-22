-- 정렬 방식 => ORDER BY
/*
	SELECT ~
	FROM table_name
	ORDER BY 컬럼 ASC|DESC
			-----
			1. 컬럼명
			2. 컬럼의 위치 번호 => 1번부터 시작
			3. 함수 
	SELECT *
	FROM emp
	   1  		2	3    4		5	 6	7	   8
	empno, ename, job, mgr, hiredate, sal, comm, deptno
			1	  2	  3	  4		5
	SELECT empno, ename, sal, deptno, hiredate
	FROM emp
	ORDER BY 3

	이중 정렬 => 대댓글
	ORDER BY sal, ename
			---
			1. =======> 같은 값을 가지고 있는 데이터끼리 정렬

	정렬 : 155 page
*/
SELECT ename, sal
FROM emp
ORDER BY sal DESC;

157 page 집계함수 => GROUP BY, JOIN, SubQuery
/*
	내장 함수 : 오라클 라이브러리 => 이미 만들어져 있는 함수
		|	
	단일행 함수 : ROW 단위
				4장 : 문자 함수 / 숫자 함수 / 날짜 함수 / 변환 함수 / 기타 함수
	집합(계) 함수 : column 단위 => 단일 함수, 컬럼을 사용할 수 없다
						    컬럼 사용시 반드시 GROUP BY 가 존재
	*** 같이 사용 할 수 없다

		SUM : column의 총합 => 장바구니 / 구매 금액
		AVG : column의 평균 => 전체 구매자의 평균 금액 => 통계
			관리자 페이지 => 그래프
		MIN : column의 최솟값
		MAX : column의 최댓값 ====> 자동 증가번호(중복이 없이)
			==> 나중엔 SEQUENCE 로 바뀜
		*** COUNT : column의 개수 : 로그인 / 아이디 중복 체크 / 검색 결과
		RANK() : 순위 출력
		DENSE_RANK() : 순위 출력

		1 2 2 4 : RANK()
		1 2 2 3 : DENSE_RANK()

		GROUP  BY => CUBE / ROLLUP
*/
/*
	142page ~ 159page
	SELECT - FROM - WHERE - GROUP BY - HAVING - ORDER BY
	=> 실행 순서
	FROM - WHERE - GROUP BY - HAVING - SELECT - ORDER BY
			: 조건 검색
	1. 연산자 => WHERE: 조건검색
		오라클에서 제공하는 연산자
		= 산술 연산자 (+ , - , * , /) => 산술만 처리
			'10' + 1 => 자동변경 => 11
			--- TO_NUMBER('10') => 10
			/ => 0으로 나눌  수 없다
			정수 / 정수 = 실수
			=> SELECT 뒤에서 통계 (총합 / 평균)
		= 비교 연산자 (= , (!= <> ^=), < , > , <=, >=
			=> true / false => WHERE 뒤에서 조건 처리
			WHERE 컬럼 연산자 값
					  ------------
					sal > 1000
					sal = 1000
				*** 같지않다 => <>(권장)
		= 논리 연산자 : AND, OR , NOT
							| 부정 ==> NOT IN, NOTBETWEEN, NOT IN
						| 범위 기간포함이 없는
					| 범위, 기간 포함
				(조건) AND (조건)
				------          ------ 두개 조건true일때 true
				(조건) OR (조건)
				------       ------ 둘중 1개 이상이 true
		= BETWEEN ~ AND : AND를 보완
				 ---	    --> 포함
			BETWEEN 10 AND 20 => 10, 20포함
			=> 페이징 =>  LIMIT을 지원
			=> AUTO_INCREMENT
		= IN : IN(값...) => 값을 포함하고 있는 데이터 추출
			=> OR여러개인 경우 / 다중 조건 검색
		= NULL : 모든 연산시 null값이 있는 경우 연산처리가 불가능
				IS NULL => NULL값인 경우
				IS NOT NULL => NULL값이 아닌 경우
		= LIKE : 유사 문자열 검색
				패턴 => %(문자 제한이 없다0_(한글자)
				검색어% => startsWith
				%검색어 => endsWith
				%검색어% => contains
	
	2. 정렬 => ORDER BY
		=> 마지막 순서
		ORDER BY 컬럼 (ASC|DESC)
		=> ASC는 생략이 가능 
		=> ORDER BY 컬럼1, 컬럼2 => 컬럼1에 같은 값이 있는 경우에만 컬럼2 정렬
		=> 컬럼 대신 => 번호 => 컬럼 위치 번호(1번)
	3. 집계 함수
		COUNT(*) => ROW의 개수
		MIN / MAX => 최댓값 / 최솟값 => 단일행 함수 / 컬럼을 같이 사용이 안됨
				=> 사용시에는 반드시 GROUP BY를 사용하면 가능
		SUM : column의 총합
		AVG : column의 평균
		RANK() / DENSE_RANK() => 순위
*/
