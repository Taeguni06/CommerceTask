<h1 style="color: rosybrown"> Commerce Task </h1> 

    src/
    └── Challenge/
    ├── Main.java
    ├── CommerceSystem.java
    ├── Product.java
    ├── Category.java
    ├── Basket.java
    └── Customer.java (CustomerGrade 포함)
password: admin123

<h2 style="color: rosybrown"> 기능 </h2>


<h3>카테고리별 상품 </h3>

    전체 상품 보기

    100만원 이하 상품 보기

    100만원 이상 상품 보기

    원하는 수량을 선택해 장바구니에 담기

<h3>장바구니</h3>

    주문하기
        -현재 등급에 따른 할인율 적용
    
    특정 상품 제거

<h3>관리자 모드</h3> 

    상품 추가 
    
    상품 수정

    상품 삭제

    전체 상품 보기

<h2 style="color: rosybrown">초기 데이터</h2>

    "Galaxy S25", 1200000, "최신 안드로이드 스마트폰", 50
    
    "iPhone 15", 1350000, "Apple의 최신 스마트폰", 30
    
    "MacBook Pro", 2400000, "M3 칩셋 탑재", 15
    
    "AirPod Pro", 350000, "노이즈 캔슬링 무선 이어폰", 10
    
    "후드 티", 60000, "따뜻한 후드티", 100
    
    "사과 1박스", 30000, "꿀사과", 40

<h2 style="color: rosybrown">적용 되는 할인율 및 등급</h2>

    BRONZE: 50만원 미만,0%

    SILVER: 50만원 ~ 100만원 미만,5%

    GOLD: 100만원 ~ 200만원 미만,10%

    PLATINUM: 200만원 이상,15%

<h2 style="color: rosybrown">사용 기술</h2>

    JAVA 25
    Stream API & Lambda Expressions
    Collections Framework (List, Map)