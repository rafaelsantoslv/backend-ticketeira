# Documentação de Endpoints - Unyx Ticket

Este documento lista os principais endpoints da API Unyx Ticket, detalhando o que cada um espera e retorna.

## 1. Autenticação

### Login
- **POST /auth/login**
- Autentica usuário e retorna token JWT.
- **Body:**
```json
{
  "email": "usuario@exemplo.com",
  "password": "senha123"
}
```
- **Response:**
```json
{
  "email": "usuario@exemplo.com",
  "name": "Nome do Usuário",
  "role": "USER",
  "token": "<jwt>"
}
```

### Registro
- **POST /auth/register**
- Cria novo usuário.
- **Body:**
```json
{
  "email": "novo@exemplo.com",
  "password": "senha123",
  "name": "Nome",
  "phone": "48999999999",
  "document": "12345678900"
}
```
- **Response:**
```json
{
  "message": "Usuário registrado com sucesso",
  "userId": "user123"
}
```

## 2. Eventos

### Listar Eventos
- **GET /events**
- Lista eventos do usuário autenticado.
- **Query:** `page`, `limit`, `status`
- **Response:**
```json
{
  "events": [ { "id": "event1", "title": "Evento" } ],
  "pagination": { "total": 1, "page": 1, "limit": 10, "pages": 1 }
}
```

### Obter Evento
- **GET /events/{eventId}**
- Detalhes de um evento.
- **Response:**
```json
{
  "id": "event1",
  "title": "Evento",
  "date": "2023-06-15T22:00:00Z"
}
```

### Criar Evento
- **POST /events**
- Cria novo evento.
- **Body:**
```json
{
  "title": "Evento",
  "date": "2023-06-15T22:00:00Z"
}
```
- **Response:**
```json
{
  "id": "event2",
  "message": "Evento criado com sucesso"
}
```

### Atualizar Evento
- **PUT /events/{eventId}**
- Atualiza evento.
- **Body:** igual ao criar
- **Response:**
```json
{
  "id": "event2",
  "message": "Evento atualizado com sucesso"
}
```

### Excluir Evento
- **DELETE /events/{eventId}**
- Remove evento.
- **Response:**
```json
{
  "message": "Evento excluído com sucesso"
}
```

## 3. Setores

### Listar Setores
- **GET /events/{eventId}/sectors**
- Lista setores do evento.
- **Response:**
```json
{
  "sectors": [ { "id": "sector1", "name": "Pista" } ]
}
```

### Criar Setor
- **POST /events/{eventId}/sectors**
- **Body:**
```json
{
  "name": "Área Premium",
  "capacity": 200
}
```
- **Response:**
```json
{
  "id": "sector2",
  "message": "Setor criado com sucesso"
}
```

### Atualizar Setor
- **PUT /events/{eventId}/sectors/{sectorId}**
- **Body:** igual ao criar
- **Response:**
```json
{
  "id": "sector2",
  "message": "Setor atualizado com sucesso"
}
```

### Excluir Setor
- **DELETE /events/{eventId}/sectors/{sectorId}**
- **Response:**
```json
{
  "message": "Setor excluído com sucesso"
}
```

## 4. Lotes

### Listar Lotes
- **GET /events/{eventId}/sectors/{sectorId}/batches**
- **Response:**
```json
{
  "batches": [ { "id": "batch1", "name": "1º Lote" } ]
}
```

### Criar Lote
- **POST /events/{eventId}/sectors/{sectorId}/batches**
- **Body:**
```json
{
  "name": "2º Lote",
  "quantity": 100,
  "price": 70
}
```
- **Response:**
```json
{
  "id": "batch2",
  "message": "Lote criado com sucesso"
}
```

### Atualizar Lote
- **PUT /events/{eventId}/sectors/{sectorId}/batches/{batchId}**
- **Body:** igual ao criar
- **Response:**
```json
{
  "id": "batch2",
  "message": "Lote atualizado com sucesso"
}
```

### Excluir Lote
- **DELETE /events/{eventId}/sectors/{sectorId}/batches/{batchId}**
- **Response:**
```json
{
  "message": "Lote excluído com sucesso"
}
```

## 5. Cupons

### Listar Cupons
- **GET /events/{eventId}/coupons**
- **Response:**
```json
{
  "coupons": [ { "id": "coupon1", "code": "PROMO10" } ]
}
```

### Criar Cupom
- **POST /events/{eventId}/coupons**
- **Body:**
```json
{
  "code": "PROMO10",
  "discountType": "percentage",
  "discountValue": 10
}
```
- **Response:**
```json
{
  "id": "coupon2",
  "message": "Cupom criado com sucesso"
}
```

### Atualizar Cupom
- **PUT /events/{eventId}/coupons/{couponId}**
- **Body:** igual ao criar
- **Response:**
```json
{
  "id": "coupon2",
  "message": "Cupom atualizado com sucesso"
}
```

### Excluir Cupom
- **DELETE /events/{eventId}/coupons/{couponId}**
- **Response:**
```json
{
  "message": "Cupom excluído com sucesso"
}
```

## 6. Cortesias

### Listar Cortesias
- **GET /events/{eventId}/courtesies**
- **Response:**
```json
{
  "courtesies": [ { "id": "courtesy1", "email": "exemplo@ex.com" } ]
}
```

### Criar Cortesia
- **POST /events/{eventId}/courtesies**
- **Body:**
```json
{
  "firstName": "João",
  "lastName": "Silva",
  "email": "joao@ex.com",
  "sectorId": "sector1"
}
```
- **Response:**
```json
{
  "id": "courtesy2",
  "message": "Cortesia gerada com sucesso"
}
```

### Excluir Cortesia
- **DELETE /events/{eventId}/courtesies/{courtesyId}**
- **Response:**
```json
{
  "message": "Cortesia excluída com sucesso"
}
```

## 7. Vendas

### Listar Vendas
- **GET /events/{eventId}/sales**
- **Response:**
```json
{
  "sales": [ { "id": "sale1", "customerName": "Ana" } ]
}
```

### Obter Venda
- **GET /events/{eventId}/sales/{saleId}**
- **Response:**
```json
{
  "id": "sale1",
  "customerName": "Ana"
}
```

## 8. Participantes

### Listar Participantes
- **GET /events/{eventId}/attendees**
- **Response:**
```json
{
  "attendees": [ { "id": "ticket1", "holderName": "Ana" } ]
}
```

### Check-in
- **POST /events/{eventId}/attendees/{ticketId}/checkin**
- **Response:**
```json
{
  "id": "ticket1",
  "checkedIn": true,
  "message": "Check-in realizado com sucesso"
}
```

### Verificar Ticket
- **GET /events/{eventId}/tickets/verify/{ticketCode}**
- **Response:**
```json
{
  "valid": true,
  "ticket": { "id": "ticket1", "holderName": "Ana" }
}
```

## 9. Dashboard

### Estatísticas do Evento
- **GET /events/{eventId}/stats**
- **Response:**
```json
{
  "totalSold": 258,
  "totalRevenue": 12580
}
```

---

Consulte a documentação oficial para detalhes completos de cada endpoint, parâmetros opcionais e exemplos avançados.
