# **목차**
1. [프로젝트 개요](#프로젝트-개요)
2. [Use Case / 유저 시나리오](https://github.com/f-lab-edu/one-day-class/edit/main/README.md#use-case--%EC%9C%A0%EC%A0%80-%EC%8B%9C%EB%82%98%EB%A6%AC%EC%98%A4)
3. [Flow Chart](https://github.com/f-lab-edu/one-day-class/edit/main/README.md#flow-chart)
4. [기능 구조도, 메뉴 구조도](#기능-구조도)

# **프로젝트 개요**
본 프로젝트에서는 원데이 클래스 서비스를 제공하는 시스템을 개발합니다. 원데이 클래스 서비스는 기본적으로 HOST가 클래스를 개설하면 GUEST가 가능한 일자에 예약(신청)하는 서비스입니다. 원데이 클래스란 하루 몇 시간 동안 일회성으로 이뤄지는 수업을 말하며 자신이 관심 있는 분야를 직접 선택해서 체험해볼 수 있다는 장점이 있습니다.

원데이 클래스 서비스를 이용하는 사용자는 GUEST, HOST, ADMIN으로 나뉩니다. GUEST와 HOST는 일반 회원으로 서비스를 이용하기 위해 회원가입이 필요하며, ADMIN은 관리자로 관리자 계정을 통해서 서비스를 관리하는 역할을 수행합니다. GUEST는 원데이 클래스 수강자로서 클래스 신청, 후기 작성, 피드 조회 등의 활동을 수행하며, HOST는 원데이 클래스 개설, 수강생 관리 등의 활동을 수행합니다.

# **Use Case / 유저 시나리오**

몇 가지 대표 케이스만 기입되어 있습니다. 모든 케이스를 확인하려면 [WIKI - Use Case / 시나리오](https://github.com/f-lab-edu/one-day-class/wiki/USE-CASE----%EC%9C%A0%EC%A0%80-%EC%8B%9C%EB%82%98%EB%A6%AC%EC%98%A4)를 참고해주세요.

## **1. GUEST**

### **USE CASE 6 - 클래스 신청**
1. 유저가 로그인한다.
2. 클래스 목록을 조회한다.
   - 2-1. 카테고리로 조회한다.
      - 2-1-1. 조회하고자 하는 지역을 선택한다.
      - 2-1-2. 최상위 카테고리 목록(카테고리명)과 카테고리 대표 사진이 유저에게 보여진다.
      - 2-1-3. 조회하고자 하는 카테고리를 선택한다.
      - 2-1-4. 선택된 카테고리의 하위 카테고리(카테고리명)들을 보여 준다.
      - 2-1-5. 최하위 카테고리를 선택할 때까지 상기 2-1-2 ~ 2-1-3번의 흐름을 반복한다.
      - 2-1-6. 선택된 최하위 카테고리에 포함된 클래스들의 목록을 보여준다.
      - 2-1-7. 3번 흐름으로 이동한다.
   - 2-2. 클래스명 검색으로 조회한다.
      - 2-2-1. 조회하고자 하는 지역을 선택한다.
      - 2-2-2. 검색어를 입력하고 검색 버튼을 클릭한다.
      - 2-2-3. 해당 지역에 개설돼있고, 해당 검색어를 포함한 클래스 목록이 보여진다.
      - 2-2-4. 3번 흐름으로 이동한다.
3. 상세한 정보를 알고 싶은 클래스 하나를 선택한다.
4. 클래스 상세 정보가 보여진다.
5. 클래스 신청하기 버튼을 클릭한다.
6. 신청 가능한 일자들이 보여지고 신청하고싶은 일자를 선택한다.
7. 해당 일자에 신청 가능한 시간대들이 보여지고 신청하고싶은 시간대를 선택한다.
8. 신청하기 버튼을 클릭한다.
   - 8-1. 신청을 성공하면 신청이 완료되었다는 메세지와 함께 신청이 완료된다.
   - 8-2. 5~7번 흐름 사이에 정원이 가득차면 정원이 가득 찼다는 메세지와 함께 신청이 실패하고 4번 흐름으로 이동한다. 

```
> 정원이 가득찬 일자나 시간대는 선택 자체가 불가능하다.
> 클래스 목록 조회 시 클래스들은 페이지당 10개씩 보여지도록 페이징 처리한다.
```

## **2. HOST**

### **USE CASE 1 - HOST 회원가입**
1. 로그인 화면에서 HOST로 가입을 선택한다.
2. HOST용 회원가입 폼이 보여진다.
3. 아이디를 입력 후 중복확인 버튼을 클릭한다.
   - 3-1. 존재하는 아이디가 있으면 중복 아이디 존재 메세지를 응답한다.
   - 3-2. 존재하는 아이디가 없으면 사용 가능 메세지를 응답한다.
4. 비밀번호와 검증 비밀번호를 입력한다.
5. 전화번호를 입력 후 인증번호 발송 버튼을 클릭한다.
6. 유저는 3분 내에 수신한 인증번호를 입력하고 인증하기 버튼을 클릭한다.
7. 유저의 성별을 선택한다.
8. 유저의 생년월일을 입력한다.
9. 주 분야(카테고리)를 선택한다.
   - 9-1. 카테고리(대분류)가 존재하지 않을 시 카테고리를 직접 입력한다.
   - 9-2. 존재하는 카테고리를 선택 시 해당 분야로 자동으로 적용된다.
10. 해당 분야의 전문가임을 입증할 수 있는 서류를 첨부한다.
11. 회원가입 버튼을 클릭하면 HOST 회원가입을 완료한다.
12. 로그인 화면으로 이동한다.
13. 관리자 승인 이후 회원가입이 완료된다.

### **USE CASE 3 - 클래스 개설**
1. 유저가 HOST로 로그인한다.
2. 클래스 탭으로 이동하여 클래스 개설 버튼을 클릭한다.
3. 개설할 클래스의 카테고리(대분류)를 선택한다.
4. 개설할 클래스의 카테고리(소분류)를 선택한다.
   - 4-1. 개설하고자 하는 클래스에 맞는 카테고리가 없는 경우 <USE CASE 6 2번 흐름>으로 이동한다.
   - 4-2. 카테고리 승인 후 2번 흐름부터 다시 시작한다.
5. 클래스 정보를 입력한다.
6. 클래스 개설 완료 버튼을 클릭한다.
   - 6-1. 현재 HOST가 개설중인 다른 클래스와 시간대가 겹치면 겹치는 클래스명과 일자, 시간대와 함께 오류 메세지를 응답한다.
   - 6-2. 클래스 최대 개설 가능 개수를 초과하여 개설하고자 하는 경우에는 오류 메세지를 응답한다.
7. 클래스 개설을 완료한다.

```
> 클래스 정보 : 클래스명, 클래스 설명, 가격, 신청 가능 일자, 수강 정원, 장소, 시간 등
> 클래스는 HOST당 5개까지 개설 가능하다.
> 카테고리를 새로 생성하여 신청한 클래스는 관리자 승인 후 개설이 완료된다.
> HOST는 자신의 분야에 해당하는 카테고리만 선택할 수 있다.
```

## **3. ADMIN**
### **USE CASE 1 - HOST 가입 심사**
1. ADMIN으로 로그인한다.
2. 가입 심사 탭으로 이동한다.
3. 가입을 신청한 HOST 유저 목록이 보여진다.
4. 심사를 원하는 유저를 선택한다.
5. 유저의 정보와 제출 서류를 확인한다.
   - 5-1. 유저가 선택한 카테고리를 확인한다.
     - 5-1-1. 이미 존재하는 카테고리를 선택했다면 HOST와 적합한 카테고리인지 검증한다.
     - 5-1-2. 카테고리를 직접 입력했다면 카테고리를 신규 등록한다.
6. 서류를 검증한다.
   - 6-1. HOST 가입을 승인한다.
   - 6-2. HOST 가입을 거절한다.
7. HOST에게 가입 심사 결과를 전송한다.

### **USE CASE 3 - 대분류 카테고리(전문 분야) 등록**
1. ADMIN으로 로그인한다.
2. 클래스 관리 탭으로 이동한다.
3. 대분류 카테고리 탭으로 이동한다.
4. 카테고리 목록이 관리자에게 보여진다.
5. 카테고리(대분류) 추가 버튼을 클릭한다.
6. 등록하고자 하는 카테고리명, 카테고리 설명, 카테고리 대표 사진을 입력하고 등록 버튼을 클릭한다.
7. 카테고리명 중복 여부를 체크한다.
   - 7-1. 이미 존재하는 카테고리명이면 존재하는 카테고리명이라는 메세지를 응답하고 4번 흐름으로 이동한다.
   - 7-2. 이외에는 8번 흐름으로 이동한다.
8. 신규 카테고리가 등록되고 이를 포함한 카테고리 목록이 관리자에게 보여진다.

# **Flow Chart**

몇 가지 대표 케이스만 기입되어 있습니다. 모든 케이스를 확인하려면 [WIKI - Flow Chart](https://github.com/f-lab-edu/one-day-class/wiki/Flow-Chart)를 참고해주세요.

## **1. GUEST**

### **클래스 신청**

## **2. HOST**

### **HOST 회원가입**
![HOST 회원가입](https://user-images.githubusercontent.com/57613635/168251444-47e86d25-2e63-4b04-ad52-a78228ba4aa0.svg)

### **클래스 개설**
![클래스 개설](https://user-images.githubusercontent.com/57613635/168258975-9112c3f5-b638-4668-8c09-d7b220287772.svg)

## **3. ADMIN**

### **HOST 가입 심사**

### **대분류 카테고리(전문 분야) 등록**

# **기능 구조도**
기능 구조도는 [WIKI - 기능 구조도](https://github.com/f-lab-edu/one-day-class/wiki/%EA%B8%B0%EB%8A%A5-%EA%B5%AC%EC%A1%B0%EB%8F%84)를 참고해주세요.

# **서버 구조도**
(TODO)

# **프로토 타입**
(TODO)

# **ERD**
(TODO)

# **브랜치 관리 전략**
## **Git Flow**
(TODO)
