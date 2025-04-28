```markdown
# Documentação da API - Unyx Ticket

Esta documentação descreve os endpoints necessários para suportar o sistema de gerenciamento de eventos e venda de ingressos Unyx Ticket.

## Índice

1. [Autenticação](#1-autenticação)
2. [Eventos](#2-eventos)
3. [Setores](#3-setores)
4. [Lotes](#4-lotes)
5. [Cupons](#5-cupons)
6. [Cortesias](#6-cortesias)
7. [Vendas](#7-vendas)
8. [Participantes](#8-participantes)
9. [Dashboard](#9-dashboard)

## Base URL

```

[https://api.unyxticket.com/v1](https://api.unyxticket.com/v1)

```plaintext

## Autenticação

Todos os endpoints (exceto login e registro) requerem autenticação via token JWT no cabeçalho:

```

Authorization: Bearer token

```plaintext

### 1. Autenticação

#### 1.1 Login

**Endpoint:** `POST /auth/login`

**Descrição:** Autentica um usuário e retorna um token JWT.

**Request Body:**
```json
{
  "email": "usuario@exemplo.com",
  "password": "senha123"
}
```

**Response (200 OK):**

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "userDetails": {
    "id": "user123",
    "email": "usuario@exemplo.com",
    "role": "ADMIN"
  }
}
```

#### 1.2 Registro

**Endpoint:** `POST /auth/register`

**Descrição:** Registra um novo usuário.

**Request Body:**

```json
{
  "email": "novousuario@exemplo.com",
  "password": "senha123"
}
```

**Response (201 Created):**

```json
{
  "message": "Usuário registrado com sucesso",
  "userId": "user456"
}
```

### 2. Eventos

#### 2.1 Listar Eventos

**Endpoint:** `GET /events`

**Descrição:** Retorna a lista de eventos do usuário autenticado.

**Query Parameters:**

- `page` (opcional): Número da página (padrão: 1)
- `limit` (opcional): Itens por página (padrão: 10)
- `status` (opcional): Filtrar por status (active, upcoming, completed, canceled)


**Response (200 OK):**

```json
{
  "events": [
    {
      "id": "event123",
      "title": "LA VIE GLOW PARTY - INAUGURAÇÃO",
      "date": "2023-06-15T22:00:00Z",
      "venueName": "Club XYZ",
      "address": "Rua das Flores, 123 - Blumenau, SC",
      "status": "active",
      "image": "https://storage.unyxticket.com/images/event123.jpg",
      "stats": {
        "totalSold": 258,
        "totalRevenue": 12580
      }
    },
    {
      "id": "event456",
      "title": "FESTIVAL DE VERÃO 2023",
      "date": "2023-12-20T18:00:00Z",
      "venueName": "Praia Central",
      "address": "Balneário Camboriú, SC",
      "status": "upcoming",
      "image": "https://storage.unyxticket.com/images/event456.jpg",
      "stats": {
        "totalSold": 450,
        "totalRevenue": 22500
      }
    }
  ],
  "pagination": {
    "total": 25,
    "page": 1,
    "limit": 10,
    "pages": 3
  }
}
```

#### 2.2 Obter Evento

**Endpoint:** `GET /events/{eventId}`

**Descrição:** Retorna os detalhes completos de um evento específico.

**Response (200 OK):**

```json
{
  "id": "event123",
  "title": "LA VIE GLOW PARTY - INAUGURAÇÃO",
  "date": "2023-06-15T22:00:00Z",
  "time": "22:00",
  "venueName": "Club XYZ",
  "address": "Rua das Flores, 123 - Blumenau, SC",
  "ageRating": "18+",
  "category": "Festa",
  "about": "Festa de inauguração com muita música eletrônica e efeitos visuais incríveis.",
  "image": "https://storage.unyxticket.com/images/event123.jpg",
  "coverImage": "https://storage.unyxticket.com/images/event123-cover.jpg",
  "status": "active",
  "stats": {
    "totalSold": 258,
    "totalRevenue": 12580,
    "ticketMedium": 48.76,
    "checkins": 132
  },
  "createdAt": "2023-05-01T14:30:00Z",
  "updatedAt": "2023-05-15T10:20:00Z"
}
```

#### 2.3 Criar Evento

**Endpoint:** `POST /events`

**Descrição:** Cria um novo evento.

**Request Body:**

```json
{
  "title": "FESTIVAL DE VERÃO 2023",
  "date": "2023-12-20T18:00:00Z",
  "time": "18:00",
  "venueName": "Praia Central",
  "address": "Balneário Camboriú, SC",
  "ageRating": "16+",
  "category": "Festival",
  "about": "O maior festival de verão da região com atrações nacionais e internacionais.",
  "image": "base64encoded...",
  "coverImage": "base64encoded..."
}
```

**Response (201 Created):**

```json
{
  "id": "event789",
  "title": "FESTIVAL DE VERÃO 2023",
  "date": "2023-12-20T18:00:00Z",
  "status": "upcoming",
  "message": "Evento criado com sucesso"
}
```

#### 2.4 Atualizar Evento

**Endpoint:** `PUT /events/{eventId}`

**Descrição:** Atualiza os dados de um evento existente.

**Request Body:**

```json
{
  "title": "FESTIVAL DE VERÃO 2023 - EDIÇÃO ESPECIAL",
  "date": "2023-12-21T18:00:00Z",
  "time": "18:00",
  "venueName": "Praia Central",
  "address": "Balneário Camboriú, SC",
  "ageRating": "16+",
  "category": "Festival",
  "about": "O maior festival de verão da região com atrações nacionais e internacionais.",
  "image": "base64encoded...",
  "coverImage": "base64encoded..."
}
```

**Response (200 OK):**

```json
{
  "id": "event789",
  "title": "FESTIVAL DE VERÃO 2023 - EDIÇÃO ESPECIAL",
  "message": "Evento atualizado com sucesso"
}
```

#### 2.5 Excluir Evento

**Endpoint:** `DELETE /events/{eventId}`

**Descrição:** Remove um evento.

**Response (200 OK):**

```json
{
  "message": "Evento excluído com sucesso"
}
```

### 3. Setores

#### 3.1 Listar Setores de um Evento

**Endpoint:** `GET /events/{eventId}/sectors`

**Descrição:** Retorna todos os setores de um evento específico.

**Response (200 OK):**

```json
{
  "sectors": [
    {
      "id": "sector123",
      "name": "Pista",
      "capacity": 500,
      "description": "Área principal do evento",
      "stats": {
        "totalSold": 258,
        "totalRevenue": 12580,
        "totalCapacity": 500
      }
    },
    {
      "id": "sector456",
      "name": "Camarote",
      "capacity": 100,
      "description": "Área VIP com vista privilegiada",
      "stats": {
        "totalSold": 0,
        "totalRevenue": 0,
        "totalCapacity": 100
      }
    }
  ]
}
```

#### 3.2 Criar Setor

**Endpoint:** `POST /events/{eventId}/sectors`

**Descrição:** Cria um novo setor para um evento.

**Request Body:**

```json
{
  "name": "Área Premium",
  "capacity": 200,
  "description": "Área exclusiva com open bar"
}
```

**Response (201 Created):**

```json
{
  "id": "sector789",
  "name": "Área Premium",
  "capacity": 200,
  "description": "Área exclusiva com open bar",
  "message": "Setor criado com sucesso"
}
```

#### 3.3 Atualizar Setor

**Endpoint:** `PUT /events/{eventId}/sectors/{sectorId}`

**Descrição:** Atualiza os dados de um setor existente.

**Request Body:**

```json
{
  "name": "Área Premium Plus",
  "capacity": 250,
  "description": "Área exclusiva com open bar e buffet"
}
```

**Response (200 OK):**

```json
{
  "id": "sector789",
  "name": "Área Premium Plus",
  "message": "Setor atualizado com sucesso"
}
```

#### 3.4 Excluir Setor

**Endpoint:** `DELETE /events/{eventId}/sectors/{sectorId}`

**Descrição:** Remove um setor de um evento.

**Response (200 OK):**

```json
{
  "message": "Setor excluído com sucesso"
}
```

### 4. Lotes

#### 4.1 Listar Lotes de um Setor

**Endpoint:** `GET /events/{eventId}/sectors/{sectorId}/batches`

**Descrição:** Retorna todos os lotes de um setor específico.

**Response (200 OK):**

```json
{
  "batches": [
    {
      "id": "batch123",
      "name": "1º Lote - Pista",
      "quantity": 200,
      "price": 50,
      "active": false,
      "sold": 200,
      "createdAt": "2023-05-01T14:30:00Z"
    },
    {
      "id": "batch456",
      "name": "2º Lote - Pista",
      "quantity": 300,
      "price": 70,
      "active": true,
      "sold": 58,
      "createdAt": "2023-05-15T10:20:00Z"
    }
  ]
}
```

#### 4.2 Criar Lote

**Endpoint:** `POST /events/{eventId}/sectors/{sectorId}/batches`

**Descrição:** Cria um novo lote para um setor.

**Request Body:**

```json
{
  "name": "3º Lote - Pista",
  "quantity": 250,
  "price": 90,
  "active": true
}
```

**Response (201 Created):**

```json
{
  "id": "batch789",
  "name": "3º Lote - Pista",
  "quantity": 250,
  "price": 90,
  "active": true,
  "message": "Lote criado com sucesso"
}
```

#### 4.3 Atualizar Lote

**Endpoint:** `PUT /events/{eventId}/sectors/{sectorId}/batches/{batchId}`

**Descrição:** Atualiza os dados de um lote existente.

**Request Body:**

```json
{
  "name": "3º Lote - Pista (Final)",
  "quantity": 200,
  "price": 100,
  "active": true
}
```

**Response (200 OK):**

```json
{
  "id": "batch789",
  "name": "3º Lote - Pista (Final)",
  "message": "Lote atualizado com sucesso"
}
```

#### 4.4 Alterar Status do Lote

**Endpoint:** `PATCH /events/{eventId}/sectors/{sectorId}/batches/{batchId}/status`

**Descrição:** Ativa ou desativa um lote.

**Request Body:**

```json
{
  "active": false
}
```

**Response (200 OK):**

```json
{
  "id": "batch789",
  "active": false,
  "message": "Status do lote atualizado com sucesso"
}
```

#### 4.5 Excluir Lote

**Endpoint:** `DELETE /events/{eventId}/sectors/{sectorId}/batches/{batchId}`

**Descrição:** Remove um lote de um setor.

**Response (200 OK):**

```json
{
  "message": "Lote excluído com sucesso"
}
```

### 5. Cupons

#### 5.1 Listar Cupons de um Evento

**Endpoint:** `GET /events/{eventId}/coupons`

**Descrição:** Retorna todos os cupons de desconto de um evento.

**Query Parameters:**

- `active` (opcional): Filtrar por status (true/false)
- `type` (opcional): Filtrar por tipo (percentage/fixed)


**Response (200 OK):**

```json
{
  "coupons": [
    {
      "id": "coupon123",
      "code": "PROMO10",
      "discountType": "percentage",
      "discountValue": 10,
      "unlimited": false,
      "usageLimit": 100,
      "usageCount": 45,
      "active": true,
      "createdAt": "2023-05-01T14:30:00Z"
    },
    {
      "id": "coupon456",
      "code": "WELCOME20",
      "discountType": "fixed",
      "discountValue": 20,
      "unlimited": true,
      "usageCount": 78,
      "active": true,
      "createdAt": "2023-05-15T10:20:00Z"
    }
  ]
}
```

#### 5.2 Criar Cupom

**Endpoint:** `POST /events/{eventId}/coupons`

**Descrição:** Cria um novo cupom de desconto para um evento.

**Request Body:**

```json
{
  "code": "SUMMER25",
  "discountType": "percentage",
  "discountValue": 25,
  "unlimited": false,
  "usageLimit": 50,
  "active": true
}
```

**Response (201 Created):**

```json
{
  "id": "coupon789",
  "code": "SUMMER25",
  "discountType": "percentage",
  "discountValue": 25,
  "message": "Cupom criado com sucesso"
}
```

#### 5.3 Gerar Código de Cupom Aleatório

**Endpoint:** `GET /events/{eventId}/coupons/generate-code`

**Descrição:** Gera um código de cupom aleatório.

**Response (200 OK):**

```json
{
  "code": "PROMO7X9F2D"
}
```

#### 5.4 Atualizar Cupom

**Endpoint:** `PUT /events/{eventId}/coupons/{couponId}`

**Descrição:** Atualiza os dados de um cupom existente.

**Request Body:**

```json
{
  "code": "SUMMER30",
  "discountType": "percentage",
  "discountValue": 30,
  "unlimited": false,
  "usageLimit": 75,
  "active": true
}
```

**Response (200 OK):**

```json
{
  "id": "coupon789",
  "code": "SUMMER30",
  "message": "Cupom atualizado com sucesso"
}
```

#### 5.5 Excluir Cupom

**Endpoint:** `DELETE /events/{eventId}/coupons/{couponId}`

**Descrição:** Remove um cupom de um evento.

**Response (200 OK):**

```json
{
  "message": "Cupom excluído com sucesso"
}
```

### 6. Cortesias

#### 6.1 Listar Cortesias de um Evento

**Endpoint:** `GET /events/{eventId}/courtesies`

**Descrição:** Retorna todas as cortesias de um evento.

**Query Parameters:**

- `sent` (opcional): Filtrar por status de envio (true/false)
- `sectorId` (opcional): Filtrar por setor


**Response (200 OK):**

```json
{
  "courtesies": [
    {
      "id": "courtesy123",
      "firstName": "João",
      "lastName": "Silva",
      "email": "joao.silva@exemplo.com",
      "sectorId": "sector123",
      "sectorName": "Pista",
      "ticketCode": "CORT-A7B9C2D3",
      "sent": true,
      "createdAt": "2023-05-01T14:30:00Z"
    },
    {
      "id": "courtesy456",
      "firstName": "Maria",
      "lastName": "Santos",
      "email": "maria.santos@exemplo.com",
      "sectorId": "sector456",
      "sectorName": "Camarote",
      "ticketCode": "CORT-E5F7G8H9",
      "sent": false,
      "createdAt": "2023-05-15T10:20:00Z"
    }
  ]
}
```

#### 6.2 Criar Cortesia

**Endpoint:** `POST /events/{eventId}/courtesies`

**Descrição:** Cria uma nova cortesia para um evento.

**Request Body:**

```json
{
  "firstName": "Pedro",
  "lastName": "Oliveira",
  "email": "pedro.oliveira@exemplo.com",
  "sectorId": "sector123"
}
```

**Response (201 Created):**

```json
{
  "id": "courtesy789",
  "firstName": "Pedro",
  "lastName": "Oliveira",
  "email": "pedro.oliveira@exemplo.com",
  "ticketCode": "CORT-J1K3L5M7",
  "message": "Cortesia gerada com sucesso"
}
```

#### 6.3 Reenviar Cortesia

**Endpoint:** `POST /events/{eventId}/courtesies/{courtesyId}/resend`

**Descrição:** Reenvia uma cortesia para o email cadastrado.

**Response (200 OK):**

```json
{
  "id": "courtesy789",
  "email": "pedro.oliveira@exemplo.com",
  "message": "Cortesia reenviada com sucesso"
}
```

#### 6.4 Excluir Cortesia

**Endpoint:** `DELETE /events/{eventId}/courtesies/{courtesyId}`

**Descrição:** Remove uma cortesia de um evento.

**Response (200 OK):**

```json
{
  "message": "Cortesia excluída com sucesso"
}
```

### 7. Vendas

#### 7.1 Listar Vendas de um Evento

**Endpoint:** `GET /events/{eventId}/sales`

**Descrição:** Retorna todas as vendas de um evento.

**Query Parameters:**

- `page` (opcional): Número da página (padrão: 1)
- `limit` (opcional): Itens por página (padrão: 10)
- `startDate` (opcional): Filtrar por data inicial (formato: YYYY-MM-DD)
- `endDate` (opcional): Filtrar por data final (formato: YYYY-MM-DD)
- `sectorId` (opcional): Filtrar por setor
- `batchId` (opcional): Filtrar por lote
- `status` (opcional): Filtrar por status (confirmed, pending, canceled)


**Response (200 OK):**

```json
{
  "sales": [
    {
      "id": "sale123",
      "customerName": "Ana Souza",
      "customerEmail": "ana.souza@exemplo.com",
      "sectorId": "sector123",
      "sectorName": "Pista",
      "batchId": "batch123",
      "batchName": "1º Lote - Pista",
      "quantity": 2,
      "unitPrice": 50,
      "totalPrice": 100,
      "discountAmount": 0,
      "finalPrice": 100,
      "paymentMethod": "credit_card",
      "status": "confirmed",
      "createdAt": "2023-05-01T14:30:00Z"
    },
    {
      "id": "sale456",
      "customerName": "Carlos Ferreira",
      "customerEmail": "carlos.ferreira@exemplo.com",
      "sectorId": "sector456",
      "sectorName": "Camarote",
      "batchId": "batch456",
      "batchName": "Camarote VIP",
      "quantity": 1,
      "unitPrice": 120,
      "totalPrice": 120,
      "discountAmount": 20,
      "finalPrice": 100,
      "couponCode": "WELCOME20",
      "paymentMethod": "pix",
      "status": "confirmed",
      "createdAt": "2023-05-15T10:20:00Z"
    }
  ],
  "pagination": {
    "total": 258,
    "page": 1,
    "limit": 10,
    "pages": 26
  }
}
```

#### 7.2 Obter Detalhes de uma Venda

**Endpoint:** `GET /events/{eventId}/sales/{saleId}`

**Descrição:** Retorna os detalhes de uma venda específica.

**Response (200 OK):**

```json
{
  "id": "sale123",
  "customerName": "Ana Souza",
  "customerEmail": "ana.souza@exemplo.com",
  "customerPhone": "+5547999887766",
  "sectorId": "sector123",
  "sectorName": "Pista",
  "batchId": "batch123",
  "batchName": "1º Lote - Pista",
  "quantity": 2,
  "unitPrice": 50,
  "totalPrice": 100,
  "discountAmount": 0,
  "finalPrice": 100,
  "paymentMethod": "credit_card",
  "paymentDetails": {
    "cardBrand": "Visa",
    "lastDigits": "1234",
    "installments": 1
  },
  "status": "confirmed",
  "tickets": [
    {
      "id": "ticket123",
      "code": "TKT-A1B2C3D4",
      "holderName": "Ana Souza",
      "checkedIn": true,
      "checkinTime": "2023-06-15T22:30:00Z"
    },
    {
      "id": "ticket124",
      "code": "TKT-E5F6G7H8",
      "holderName": "Roberto Souza",
      "checkedIn": false,
      "checkinTime": null
    }
  ],
  "createdAt": "2023-05-01T14:30:00Z",
  "updatedAt": "2023-05-01T14:35:00Z"
}
```

### 8. Participantes

#### 8.1 Listar Participantes de um Evento

**Endpoint:** `GET /events/{eventId}/attendees`

**Descrição:** Retorna todos os participantes de um evento.

**Query Parameters:**

- `page` (opcional): Número da página (padrão: 1)
- `limit` (opcional): Itens por página (padrão: 10)
- `search` (opcional): Buscar por nome ou email
- `sectorId` (opcional): Filtrar por setor
- `checkedIn` (opcional): Filtrar por status de check-in (true/false)


**Response (200 OK):**

```json
{
  "attendees": [
    {
      "id": "ticket123",
      "code": "TKT-A1B2C3D4",
      "holderName": "Ana Souza",
      "holderEmail": "ana.souza@exemplo.com",
      "sectorId": "sector123",
      "sectorName": "Pista",
      "batchName": "1º Lote - Pista",
      "checkedIn": true,
      "checkinTime": "2023-06-15T22:30:00Z",
      "saleId": "sale123",
      "isCourtesy": false
    },
    {
      "id": "ticket456",
      "code": "CORT-J1K3L5M7",
      "holderName": "Pedro Oliveira",
      "holderEmail": "pedro.oliveira@exemplo.com",
      "sectorId": "sector123",
      "sectorName": "Pista",
      "checkedIn": false,
      "checkinTime": null,
      "isCourtesy": true
    }
  ],
  "pagination": {
    "total": 258,
    "page": 1,
    "limit": 10,
    "pages": 26
  }
}
```

#### 8.2 Realizar Check-in

**Endpoint:** `POST /events/{eventId}/attendees/{ticketId}/checkin`

**Descrição:** Realiza o check-in de um participante.

**Response (200 OK):**

```json
{
  "id": "ticket456",
  "code": "CORT-J1K3L5M7",
  "holderName": "Pedro Oliveira",
  "checkedIn": true,
  "checkinTime": "2023-06-15T23:15:00Z",
  "message": "Check-in realizado com sucesso"
}
```

#### 8.3 Verificar Ticket

**Endpoint:** `GET /events/{eventId}/tickets/verify/{ticketCode}`

**Descrição:** Verifica a validade de um ticket pelo código.

**Response (200 OK):**

```json
{
  "valid": true,
  "ticket": {
    "id": "ticket456",
    "code": "CORT-J1K3L5M7",
    "holderName": "Pedro Oliveira",
    "holderEmail": "pedro.oliveira@exemplo.com",
    "sectorId": "sector123",
    "sectorName": "Pista",
    "checkedIn": false,
    "isCourtesy": true
  }
}
```

### 9. Dashboard

#### 9.1 Obter Estatísticas do Evento

**Endpoint:** `GET /events/{eventId}/stats`

**Descrição:** Retorna estatísticas gerais do evento.

**Response (200 OK):**

```json
{
  "totalSold": 258,
  "totalRevenue": 12580,
  "ticketMedium": 48.76,
  "checkins": 132,
  "sectors": [
    {
      "id": "sector123",
      "name": "Pista",
      "totalSold": 258,
      "totalRevenue": 12580,
      "capacity": 500,
      "percentageSold": 51.6
    },
    {
      "id": "sector456",
      "name": "Camarote",
      "totalSold": 0,
      "totalRevenue": 0,
      "capacity": 100,
      "percentageSold": 0
    }
  ],
  "batches": [
    {
      "id": "batch123",
      "name": "1º Lote - Pista",
      "sectorId": "sector123",
      "sectorName": "Pista",
      "sold": 200,
      "quantity": 200,
      "percentageSold": 100,
      "revenue": 10000
    },
    {
      "id": "batch456",
      "name": "2º Lote - Pista",
      "sectorId": "sector123",
      "sectorName": "Pista",
      "sold": 58,
      "quantity": 300,
      "percentageSold": 19.3,
      "revenue": 4060
    }
  ]
}
```

#### 9.2 Obter Vendas por Período

**Endpoint:** `GET /events/{eventId}/stats/sales-by-period`

**Descrição:** Retorna estatísticas de vendas por período.

**Query Parameters:**

- `period` (obrigatório): Período de agrupamento (day, week, month)
- `startDate` (opcional): Data inicial (formato: YYYY-MM-DD)
- `endDate` (opcional): Data final (formato: YYYY-MM-DD)


**Response (200 OK):**

```json
{
  "period": "day",
  "data": [
    {
      "date": "2023-05-01",
      "count": 15,
      "revenue": 750
    },
    {
      "date": "2023-05-02",
      "count": 23,
      "revenue": 1150
    },
    {
      "date": "2023-05-03",
      "count": 18,
      "revenue": 900
    }
  ]
}
```

#### 9.3 Obter Estatísticas de Cupons

**Endpoint:** `GET /events/{eventId}/stats/coupons`

**Descrição:** Retorna estatísticas de uso de cupons.

**Response (200 OK):**

```json
{
  "totalCoupons": 5,
  "totalUsage": 123,
  "totalDiscountAmount": 2460,
  "coupons": [
    {
      "id": "coupon123",
      "code": "PROMO10",
      "discountType": "percentage",
      "  [
    {
      "id": "coupon123",
      "code": "PROMO10",
      "discountType": "percentage",
      "discountValue": 10,
      "usageCount": 45,
      "discountAmount": 450,
      "conversionRate": 4.5
    },
    {
      "id": "coupon456",
      "code": "WELCOME20",
      "discountType": "fixed",
      "discountValue": 20,
      "usageCount": 78,
      "discountAmount": 1560,
      "conversionRate": 7.8
    }
  ]
}
```

## Códigos de Erro

A API retorna os seguintes códigos de status HTTP:

- `200 OK`: Requisição bem-sucedida
- `201 Created`: Recurso criado com sucesso
- `400 Bad Request`: Parâmetros inválidos ou ausentes
- `401 Unauthorized`: Autenticação necessária ou inválida
- `403 Forbidden`: Usuário não tem permissão para acessar o recurso
- `404 Not Found`: Recurso não encontrado
- `409 Conflict`: Conflito ao criar ou atualizar recurso (ex: código de cupom já existe)
- `422 Unprocessable Entity`: Dados válidos, mas não processáveis (ex: lote esgotado)
- `500 Internal Server Error`: Erro interno do servidor


## Formato de Erro

Quando ocorre um erro, a API retorna um objeto JSON com a seguinte estrutura:

```json
{
  "error": {
    "code": "RESOURCE_NOT_FOUND",
    "message": "O evento solicitado não foi encontrado",
    "details": {
      "resourceId": "event999",
      "resourceType": "Event"
    }
  }
}
```

## Considerações de Implementação

1. **Autenticação**: Implementar JWT para autenticação e controle de acesso.
2. **Validação**: Validar todos os dados de entrada para garantir integridade.
3. **Paginação**: Implementar paginação em endpoints que retornam muitos resultados.
4. **Cache**: Considerar cache para endpoints frequentemente acessados.
5. **Rate Limiting**: Implementar limitação de taxa para evitar abusos.
6. **Logs**: Manter logs detalhados para auditoria e depuração.
7. **Documentação**: Manter esta documentação atualizada conforme a API evolui.


## Webhooks

Para integrações em tempo real, a API também oferece webhooks para os seguintes eventos:

1. `sale.created`: Quando uma nova venda é realizada
2. `sale.confirmed`: Quando uma venda é confirmada (pagamento aprovado)
3. `sale.canceled`: Quando uma venda é cancelada
4. `ticket.checkin`: Quando um check-in é realizado
5. `batch.soldout`: Quando um lote esgota


Para configurar webhooks, use o endpoint:

**Endpoint:** `POST /webhooks`

**Request Body:**

```json
{
  "url": "https://seu-dominio.com/webhook-handler",
  "events": ["sale.created", "sale.confirmed"],
  "secret": "seu_segredo_para_verificacao"
}
```

**Response (201 Created):**

```json
{
  "id": "webhook123",
  "url": "https://seu-dominio.com/webhook-handler",
  "events": ["sale.created", "sale.confirmed"],
  "message": "Webhook configurado com sucesso"
}
```

```plaintext

Para salvar este conteúdo como um arquivo, você pode:

1. Copiar todo o texto acima
2. Colar em um editor de texto como VS Code, Notepad++, ou qualquer outro editor de sua preferência
3. Salvar o arquivo com a extensão `.md` (por exemplo, `api-documentation.md`)

Você também pode usar ferramentas online como GitHub Gist ou serviços como Notion para armazenar e compartilhar esta documentação.

```