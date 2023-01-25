# _Pieces of Life Server_

![스크린샷_20230110_112116](https://user-images.githubusercontent.com/86697585/212994369-643230b7-149e-4c56-9311-9a620b00aa8b.png)

http://3.39.155.109:8080

</br>

## 노션 페이지
https://www.notion.so/Pieces-Of-Life-080927ee5be24e16ae7a4fb2441fd38c

</br>

## ERD
https://www.erdcloud.com/d/38Ho6AmahSCgZEz8B
</br>_erd 완성 후 이미지넣기_

</br>

## 팀원

<aside>
    
✨ [김해찬](https://github.com/bluesun147) - 

✨ [김경민](https://github.com/bluesun147) - 

✨ [김준기](https://github.com/kjbig) - 
  
✨ [박현정](https://github.com/hyeonjeongs) - 

</aside>

</br>

## Commit/PR Convention
_지라 이슈와 연동해 커밋. (ex : POL-10 ✨ FEAT : Add user)_

_지라 이슈와 연동해 PR. (ex : [POL-10] FEAT : Add user)_

| Type       | 설명                                                          |
| ---------- | ------------------------------------------------------------- |
| [FEAT]     | 새로운 기능 추가                                               |
| [FIX]      | 버그 수정                                                     |
| [REFACTOR] | 코드 리팩토링                                                 |
| [DOCS]     | 문서 수정                                                     |
| [ADD]      | 파일, 부가 가능 추가                                          |
| [TEST]     | 테스트 코드                                                   |
| [CHORE]    | 그 외 자잘한 수정                                             |

</br>

## Issue Convention
_제목 자유롭게 작성하고 관련 Label을 설정해주세요_

| Label       | 설명                                                          |
| ---------- | ------------------------------------------------------------- |
| 🐞bug    | 버그                                                          |
| 🧹chore  | chore                                                        |
| 🗑️delete | 삭제                                                         |
| 📄docs   | 문서                                                       |
| ⚙️env    | 환경 설정                                                   |
| 💡feature | 기능 개발                                                |
| 🛠️fix    | fix                                                         |
| ⚡refactor | 리팩토링                                                |
| 👷test    | 테스트                                                      |

</br>

## Coding Convention


 <details>  <summary>1. 변수</summary>  
 <div markdown="1"> 
 <br>
     1-1. camelCase 형식을 사용합니다.<br><br>
     1-2. 이름은 짧지만 의미 있어야 합니다.(사용 의도를 누구나 알아낼 수 있도록!)<br><br>
     1-3. ENUM이나 상수는 대문자로 표기합니다.<br><br>
 </div>  </details>

 <details>  <summary>2. 함수</summary>  
 <div markdown="1"> 
 <br>
 2-1. 함수의 이름은 동사여야 하며, camelCase 형식을 사용합니다. <br><br>
     2-2. 객체 이름을 함수 이름에 중복적으로 사용하지 않습니다.<br><br>
     </div>  </details>

 <details>  <summary>3. 클래스 </summary>  
 <div markdown="1"> 
 <br>
 클래스 이름은 명사이어야 하며 Pascal Case를 사용합니다.
     </div>  </details>

 <details>  <summary>4. 인터페이스 </summary>  
 <div markdown="1"> 
 <br>
 클래스와 같은 규칙을 사용합니다.
     </div>  </details>




</br>

## Branch Strategy
### git flow
- master : 배포
- develop : 기능 개발
- feature : 추가 가능 개발, develop 브랜치에 머지. _(브랜치 명 ex : feature/login)_
- release : QA 테스트 진행
- hotfix : master 브랜치 버그 수정

issue를 기반한 작업단위, 기능단위로 생성합니다!

issue 만들면 생성되는 번호 + issue 간략 설명을 이용해 브랜치를 만듭니다.

_feat / POL-2 - view_user_


</br>

## API
_노션 api 명세서_

</br>
