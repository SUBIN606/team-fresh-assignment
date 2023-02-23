## VOC 등록
고객사로부터 인입된 클레임을 VOC로 등록합니다.
### Request
```text
POST /vocs

{
  "content": "~~한 이유로 ~~한 클레임 접수",
  "cause": "~~~한 이유로 ~~~한 사유가 있음",
  "target": "CARRIER",
  "targetCompanyId": 4,
  "customerManagerId": 1,
  "createdBy": 1001
}
```

## VOC 목록 조회
### Request
```text
GET /vocs
```

## 배상정보 등록
VOC 건에 대한 배상정보를 등록합니다.
```text
POST /vocs/{vocId}/compensations

{
    "amount": 10000
}
```

## 배상목록 조회
```text
GET /compensations
```

## 패널티
### 패널티 등록
```text
POST /compensations/{compensationId}/penalties

{
  "owner": 3,
  "content": "~~~한 이유로 패널티를 발급합니다."
}
```

### 패널티 확인 여부 등록
```text
PATCH /penalties/{penaltyId}/read
```
