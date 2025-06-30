### Documentação do Banco de Dados - Unyx Ticket

## Visão Geral

Este documento descreve a estrutura do banco de dados para o sistema Unyx Ticket, uma plataforma de gerenciamento de eventos e venda de ingressos. O banco de dados foi projetado para suportar todas as funcionalidades do sistema, incluindo gerenciamento de eventos, vendas de ingressos, usuários, pagamentos e relatórios.

## Diagrama ER

```mermaid
Diagrama ER do Unyx Ticket.download-icon {
            cursor: pointer;
            transform-origin: center;
        }
        .download-icon .arrow-part {
            transition: transform 0.35s cubic-bezier(0.35, 0.2, 0.14, 0.95);
             transform-origin: center;
        }
        button:has(.download-icon):hover .download-icon .arrow-part, button:has(.download-icon):focus-visible .download-icon .arrow-part {
          transform: translateY(-1.5px);
        }
        #mermaid-diagram-r3v3{font-family:var(--font-geist-sans);font-size:12px;fill:#000000;}#mermaid-diagram-r3v3 .error-icon{fill:#552222;}#mermaid-diagram-r3v3 .error-text{fill:#552222;stroke:#552222;}#mermaid-diagram-r3v3 .edge-thickness-normal{stroke-width:1px;}#mermaid-diagram-r3v3 .edge-thickness-thick{stroke-width:3.5px;}#mermaid-diagram-r3v3 .edge-pattern-solid{stroke-dasharray:0;}#mermaid-diagram-r3v3 .edge-thickness-invisible{stroke-width:0;fill:none;}#mermaid-diagram-r3v3 .edge-pattern-dashed{stroke-dasharray:3;}#mermaid-diagram-r3v3 .edge-pattern-dotted{stroke-dasharray:2;}#mermaid-diagram-r3v3 .marker{fill:#666;stroke:#666;}#mermaid-diagram-r3v3 .marker.cross{stroke:#666;}#mermaid-diagram-r3v3 svg{font-family:var(--font-geist-sans);font-size:12px;}#mermaid-diagram-r3v3 p{margin:0;}#mermaid-diagram-r3v3 .entityBox{fill:#eee;stroke:#999;}#mermaid-diagram-r3v3 .attributeBoxOdd{fill:#ffffff;stroke:#999;}#mermaid-diagram-r3v3 .attributeBoxEven{fill:#f2f2f2;stroke:#999;}#mermaid-diagram-r3v3 .relationshipLabelBox{fill:hsl(-160, 0%, 93.3333333333%);opacity:0.7;background-color:hsl(-160, 0%, 93.3333333333%);}#mermaid-diagram-r3v3 .relationshipLabelBox rect{opacity:0.5;}#mermaid-diagram-r3v3 .relationshipLine{stroke:#666;}#mermaid-diagram-r3v3 .entityTitleText{text-anchor:middle;font-size:18px;fill:#000000;}#mermaid-diagram-r3v3 #MD_PARENT_START{fill:#f5f5f5!important;stroke:#666!important;stroke-width:1;}#mermaid-diagram-r3v3 #MD_PARENT_END{fill:#f5f5f5!important;stroke:#666!important;stroke-width:1;}#mermaid-diagram-r3v3 .flowchart-link{stroke:hsl(var(--gray-400));stroke-width:1px;}#mermaid-diagram-r3v3 .marker,#mermaid-diagram-r3v3 marker,#mermaid-diagram-r3v3 marker *{fill:hsl(var(--gray-400))!important;stroke:hsl(var(--gray-400))!important;}#mermaid-diagram-r3v3 .label,#mermaid-diagram-r3v3 text,#mermaid-diagram-r3v3 text>tspan{fill:hsl(var(--black))!important;color:hsl(var(--black))!important;}#mermaid-diagram-r3v3 .background,#mermaid-diagram-r3v3 rect.relationshipLabelBox{fill:hsl(var(--white))!important;}#mermaid-diagram-r3v3 .entityBox,#mermaid-diagram-r3v3 .attributeBoxEven{fill:hsl(var(--gray-150))!important;}#mermaid-diagram-r3v3 .attributeBoxOdd{fill:hsl(var(--white))!important;}#mermaid-diagram-r3v3 .label-container,#mermaid-diagram-r3v3 rect.actor{fill:hsl(var(--white))!important;stroke:hsl(var(--gray-400))!important;}#mermaid-diagram-r3v3 line{stroke:hsl(var(--gray-400))!important;}#mermaid-diagram-r3v3 :root{--mermaid-font-family:var(--font-geist-sans);}USERSEVENTSORDERSSECTORSBATCHESTICKETSCOUPONSPAYMENTSUSER_ROLESROLESEVENT_CATEGORIESCATEGORIESCHECK_INScreatesplacescontainshasbelongs_tobelongs_tohascontainshashasassigned_tocategorized_bycontainshas
```

## Tabelas

### users

Armazena informações sobre os usuários do sistema, incluindo clientes e administradores.

| Coluna | Tipo | Restrições | Descrição
|-----|-----|-----|-----
| id | uuid | PRIMARY KEY | Identificador único do usuário
| name | varchar(255) | NOT NULL | Nome completo do usuário
| email | varchar(255) | NOT NULL, UNIQUE | Email do usuário (usado para login)
| password | varchar(255) | NOT NULL | Senha criptografada
| phone | varchar(20) |  | Número de telefone
| document | varchar(20) |  | Documento de identificação (CPF/CNPJ)
| birth_date | date |  | Data de nascimento
| profile_image | varchar(255) |  | URL da imagem de perfil
| is_active | boolean | DEFAULT true | Status de ativação da conta
| email_verified | boolean | DEFAULT false | Indica se o email foi verificado
| created_at | timestamp | DEFAULT now() | Data de criação do registro
| updated_at | timestamp | DEFAULT now() | Data da última atualização


**Índices:**

- `idx_users_email` em `email` para otimizar buscas por email


### roles

Define os papéis/funções disponíveis no sistema.

| Coluna | Tipo | Restrições | Descrição
|-----|-----|-----|-----
| id | uuid | PRIMARY KEY | Identificador único do papel
| name | varchar(50) | NOT NULL, UNIQUE | Nome do papel (admin, manager, customer, etc.)
| description | text |  | Descrição das permissões do papel
| created_at | timestamp | DEFAULT now() | Data de criação do registro
| updated_at | timestamp | DEFAULT now() | Data da última atualização


### user_roles

Tabela de junção para relacionamento muitos-para-muitos entre usuários e papéis.

| Coluna | Tipo | Restrições | Descrição
|-----|-----|-----|-----
| id | uuid | PRIMARY KEY | Identificador único da atribuição
| user_id | uuid | NOT NULL, FOREIGN KEY | Referência ao usuário
| role_id | uuid | NOT NULL, FOREIGN KEY | Referência ao papel
| created_at | timestamp | DEFAULT now() | Data de criação do registro


**Restrições:**

- `FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE`
- `FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE`


### events

Armazena informações sobre os eventos disponíveis na plataforma.

| Coluna | Tipo | Restrições | Descrição
|-----|-----|-----|-----
| id | uuid | PRIMARY KEY | Identificador único do evento
| creator_id | uuid | NOT NULL, FOREIGN KEY | Referência ao usuário criador
| title | varchar(255) | NOT NULL | Título do evento
| slug | varchar(255) | NOT NULL, UNIQUE | Slug para URL amigável
| description | text |  | Descrição detalhada do evento
| short_description | varchar(500) |  | Descrição curta para listagens
| age_rating | varchar(10) |  | Classificação indicativa
| start_date | timestamp | NOT NULL | Data e hora de início
| end_date | timestamp |  | Data e hora de término
| location_name | varchar(255) | NOT NULL | Nome do local do evento
| location_address | text |  | Endereço completo
| location_city | varchar(100) |  | Cidade
| location_state | varchar(100) |  | Estado
| location_zip | varchar(20) |  | CEP
| location_coordinates | point |  | Coordenadas geográficas (latitude, longitude)
| cover_image | varchar(255) |  | URL da imagem de capa
| banner_image | varchar(255) |  | URL da imagem de banner
| is_published | boolean | DEFAULT false | Indica se o evento está publicado
| is_featured | boolean | DEFAULT false | Indica se o evento é destaque
| max_tickets_per_order | integer | DEFAULT 10 | Máximo de ingressos por pedido
| created_at | timestamp | DEFAULT now() | Data de criação do registro
| updated_at | timestamp | DEFAULT now() | Data da última atualização


**Índices:**

- `idx_events_slug` em `slug` para otimizar buscas por slug
- `idx_events_start_date` em `start_date` para otimizar buscas por data


**Restrições:**

- `FOREIGN KEY (creator_id) REFERENCES users(id) ON DELETE RESTRICT`


### categories

Categorias para classificação de eventos.

| Coluna | Tipo | Restrições | Descrição
|-----|-----|-----|-----
| id | uuid | PRIMARY KEY | Identificador único da categoria
| name | varchar(100) | NOT NULL, UNIQUE | Nome da categoria
| slug | varchar(100) | NOT NULL, UNIQUE | Slug para URL amigável
| description | text |  | Descrição da categoria
| icon | varchar(255) |  | Ícone representativo
| created_at | timestamp | DEFAULT now() | Data de criação do registro
| updated_at | timestamp | DEFAULT now() | Data da última atualização


### event_categories

Tabela de junção para relacionamento muitos-para-muitos entre eventos e categorias.

| Coluna | Tipo | Restrições | Descrição
|-----|-----|-----|-----
| id | uuid | PRIMARY KEY | Identificador único da atribuição
| event_id | uuid | NOT NULL, FOREIGN KEY | Referência ao evento
| category_id | uuid | NOT NULL, FOREIGN KEY | Referência à categoria
| created_at | timestamp | DEFAULT now() | Data de criação do registro


**Restrições:**

- `FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE`
- `FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE`


### sectors

Setores de um evento (áreas com diferentes tipos de ingressos).

| Coluna | Tipo | Restrições | Descrição
|-----|-----|-----|-----
| id | uuid | PRIMARY KEY | Identificador único do setor
| event_id | uuid | NOT NULL, FOREIGN KEY | Referência ao evento
| name | varchar(100) | NOT NULL | Nome do setor
| description | text |  | Descrição do setor
| capacity | integer | NOT NULL | Capacidade máxima do setor
| has_numbered_seats | boolean | DEFAULT false | Indica se possui assentos numerados
| map_image | varchar(255) |  | URL da imagem do mapa de assentos
| map_coordinates | json |  | Coordenadas para mapa interativo
| created_at | timestamp | DEFAULT now() | Data de criação do registro
| updated_at | timestamp | DEFAULT now() | Data da última atualização


**Restrições:**

- `FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE`


### batches

Lotes de ingressos com preços e períodos específicos.

| Coluna | Tipo | Restrições | Descrição
|-----|-----|-----|-----
| id | uuid | PRIMARY KEY | Identificador único do lote
| event_id | uuid | NOT NULL, FOREIGN KEY | Referência ao evento
| name | varchar(100) | NOT NULL | Nome do lote (ex: "1º Lote", "Lote Promocional")
| description | text |  | Descrição do lote
| start_date | timestamp |  | Data de início das vendas
| end_date | timestamp |  | Data de término das vendas
| quantity | integer |  | Quantidade disponível (NULL = ilimitado)
| is_active | boolean | DEFAULT true | Indica se o lote está ativo
| order | integer | DEFAULT 0 | Ordem de exibição/prioridade
| created_at | timestamp | DEFAULT now() | Data de criação do registro
| updated_at | timestamp | DEFAULT now() | Data da última atualização


**Restrições:**

- `FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE`


### tickets

Tipos de ingressos disponíveis para um evento.

| Coluna | Tipo | Restrições | Descrição
|-----|-----|-----|-----
| id | uuid | PRIMARY KEY | Identificador único do tipo de ingresso
| event_id | uuid | NOT NULL, FOREIGN KEY | Referência ao evento
| sector_id | uuid | NOT NULL, FOREIGN KEY | Referência ao setor
| batch_id | uuid | NOT NULL, FOREIGN KEY | Referência ao lote
| name | varchar(100) | NOT NULL | Nome do tipo de ingresso
| description | text |  | Descrição do tipo de ingresso
| price | decimal(10,2) | NOT NULL | Preço base
| service_fee | decimal(10,2) | DEFAULT 0 | Taxa de serviço
| is_active | boolean | DEFAULT true | Indica se está disponível para venda
| max_per_order | integer |  | Máximo por pedido (NULL = sem limite)
| requires_identification | boolean | DEFAULT false | Exige identificação nominal
| created_at | timestamp | DEFAULT now() | Data de criação do registro
| updated_at | timestamp | DEFAULT now() | Data da última atualização


**Restrições:**

- `FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE`
- `FOREIGN KEY (sector_id) REFERENCES sectors(id) ON DELETE CASCADE`
- `FOREIGN KEY (batch_id) REFERENCES batches(id) ON DELETE CASCADE`


### coupons

Cupons de desconto para eventos.

| Coluna | Tipo | Restrições | Descrição
|-----|-----|-----|-----
| id | uuid | PRIMARY KEY | Identificador único do cupom
| event_id | uuid | NOT NULL, FOREIGN KEY | Referência ao evento
| code | varchar(50) | NOT NULL, UNIQUE | Código do cupom
| description | text |  | Descrição do cupom
| discount_type | varchar(20) | NOT NULL | Tipo de desconto (percentage, fixed)
| discount_value | decimal(10,2) | NOT NULL | Valor do desconto
| start_date | timestamp |  | Data de início da validade
| end_date | timestamp |  | Data de término da validade
| usage_limit | integer |  | Limite de usos (NULL = ilimitado)
| usage_count | integer | DEFAULT 0 | Contador de usos
| min_purchase | decimal(10,2) |  | Valor mínimo de compra
| is_active | boolean | DEFAULT true | Indica se o cupom está ativo
| created_at | timestamp | DEFAULT now() | Data de criação do registro
| updated_at | timestamp | DEFAULT now() | Data da última atualização


**Índices:**

- `idx_coupons_code` em `code` para otimizar buscas por código


**Restrições:**

- `FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE`


### orders

Pedidos de compra de ingressos.

| Coluna | Tipo | Restrições | Descrição
|-----|-----|-----|-----
| id | uuid | PRIMARY KEY | Identificador único do pedido
| user_id | uuid | NOT NULL, FOREIGN KEY | Referência ao usuário comprador
| event_id | uuid | NOT NULL, FOREIGN KEY | Referência ao evento
| order_number | varchar(50) | NOT NULL, UNIQUE | Número do pedido (para exibição)
| status | varchar(20) | NOT NULL | Status do pedido (pending, paid, canceled, etc.)
| subtotal | decimal(10,2) | NOT NULL | Valor subtotal
| discount | decimal(10,2) | DEFAULT 0 | Valor de desconto aplicado
| fees | decimal(10,2) | DEFAULT 0 | Taxas aplicadas
| total | decimal(10,2) | NOT NULL | Valor total
| coupon_id | uuid |  | Referência ao cupom utilizado
| coupon_code | varchar(50) |  | Código do cupom (para histórico)
| notes | text |  | Observações do pedido
| ip_address | varchar(45) |  | Endereço IP do comprador
| user_agent | text |  | User agent do navegador
| created_at | timestamp | DEFAULT now() | Data de criação do registro
| updated_at | timestamp | DEFAULT now() | Data da última atualização
| expires_at | timestamp |  | Data de expiração do pedido pendente


**Índices:**

- `idx_orders_order_number` em `order_number` para otimizar buscas por número
- `idx_orders_user_id` em `user_id` para otimizar buscas por usuário
- `idx_orders_event_id` em `event_id` para otimizar buscas por evento


**Restrições:**

- `FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE RESTRICT`
- `FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE RESTRICT`
- `FOREIGN KEY (coupon_id) REFERENCES coupons(id) ON DELETE SET NULL`


### order_items

Itens individuais de um pedido (ingressos).

| Coluna | Tipo | Restrições | Descrição
|-----|-----|-----|-----
| id | uuid | PRIMARY KEY | Identificador único do item
| order_id | uuid | NOT NULL, FOREIGN KEY | Referência ao pedido
| ticket_id | uuid | NOT NULL, FOREIGN KEY | Referência ao tipo de ingresso
| ticket_code | varchar(100) | NOT NULL, UNIQUE | Código único do ingresso
| attendee_name | varchar(255) |  | Nome do participante
| attendee_email | varchar(255) |  | Email do participante
| attendee_document | varchar(20) |  | Documento do participante
| unit_price | decimal(10,2) | NOT NULL | Preço unitário no momento da compra
| service_fee | decimal(10,2) | DEFAULT 0 | Taxa de serviço no momento da compra
| status | varchar(20) | NOT NULL | Status do ingresso (valid, used, canceled, etc.)
| seat_info | json |  | Informações do assento (para assentos numerados)
| qr_code | varchar(255) |  | URL ou dados do QR code
| created_at | timestamp | DEFAULT now() | Data de criação do registro
| updated_at | timestamp | DEFAULT now() | Data da última atualização


**Índices:**

- `idx_order_items_ticket_code` em `ticket_code` para otimizar buscas por código


**Restrições:**

- `FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE`
- `FOREIGN KEY (ticket_id) REFERENCES tickets(id) ON DELETE RESTRICT`


### payments

Registros de pagamentos associados a pedidos.

| Coluna | Tipo | Restrições | Descrição
|-----|-----|-----|-----
| id | uuid | PRIMARY KEY | Identificador único do pagamento
| order_id | uuid | NOT NULL, FOREIGN KEY | Referência ao pedido
| provider | varchar(50) | NOT NULL | Provedor de pagamento (stripe, paypal, etc.)
| provider_payment_id | varchar(255) |  | ID da transação no provedor
| method | varchar(50) | NOT NULL | Método de pagamento (credit_card, pix, etc.)
| amount | decimal(10,2) | NOT NULL | Valor do pagamento
| currency | varchar(3) | NOT NULL, DEFAULT 'BRL' | Moeda do pagamento
| status | varchar(20) | NOT NULL | Status do pagamento
| payment_data | json |  | Dados adicionais do pagamento
| paid_at | timestamp |  | Data de confirmação do pagamento
| created_at | timestamp | DEFAULT now() | Data de criação do registro
| updated_at | timestamp | DEFAULT now() | Data da última atualização


**Restrições:**

- `FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE`


### check_ins

Registros de check-in de ingressos no evento.

| Coluna | Tipo | Restrições | Descrição
|-----|-----|-----|-----
| id | uuid | PRIMARY KEY | Identificador único do check-in
| order_item_id | uuid | NOT NULL, FOREIGN KEY | Referência ao item do pedido (ingresso)
| event_id | uuid | NOT NULL, FOREIGN KEY | Referência ao evento
| checked_by | uuid | NOT NULL, FOREIGN KEY | Referência ao usuário que realizou o check-in
| check_in_time | timestamp | NOT NULL, DEFAULT now() | Data e hora do check-in
| location | varchar(100) |  | Local do check-in (portão, entrada, etc.)
| device_info | json |  | Informações sobre o dispositivo usado
| notes | text |  | Observações adicionais


**Restrições:**

- `FOREIGN KEY (order_item_id) REFERENCES order_items(id) ON DELETE CASCADE`
- `FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE`
- `FOREIGN KEY (checked_by) REFERENCES users(id) ON DELETE RESTRICT`


### user_preferences

Preferências e configurações dos usuários.

| Coluna | Tipo | Restrições | Descrição
|-----|-----|-----|-----
| id | uuid | PRIMARY KEY | Identificador único da preferência
| user_id | uuid | NOT NULL, FOREIGN KEY | Referência ao usuário
| theme | varchar(20) | DEFAULT 'light' | Tema da interface (light, dark, system)
| email_notifications | boolean | DEFAULT true | Receber notificações por email
| push_notifications | boolean | DEFAULT true | Receber notificações push
| language | varchar(5) | DEFAULT 'pt-BR' | Idioma preferido
| timezone | varchar(50) | DEFAULT 'America/Sao_Paulo' | Fuso horário
| created_at | timestamp | DEFAULT now() | Data de criação do registro
| updated_at | timestamp | DEFAULT now() | Data da última atualização


**Restrições:**

- `FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE`


### notifications

Notificações enviadas aos usuários.

| Coluna | Tipo | Restrições | Descrição
|-----|-----|-----|-----
| id | uuid | PRIMARY KEY | Identificador único da notificação
| user_id | uuid | NOT NULL, FOREIGN KEY | Referência ao usuário destinatário
| title | varchar(255) | NOT NULL | Título da notificação
| content | text | NOT NULL | Conteúdo da notificação
| type | varchar(50) | NOT NULL | Tipo de notificação (system, event, order, etc.)
| read | boolean | DEFAULT false | Indica se foi lida
| read_at | timestamp |  | Data de leitura
| action_url | varchar(255) |  | URL de ação relacionada
| created_at | timestamp | DEFAULT now() | Data de criação do registro


**Restrições:**

- `FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE`


## Índices Adicionais

Além dos índices já mencionados, recomenda-se a criação dos seguintes índices para otimizar consultas frequentes:

1. `idx_events_is_published_start_date` em `events(is_published, start_date)` para listagens de eventos publicados por data
2. `idx_tickets_event_id_is_active` em `tickets(event_id, is_active)` para busca de ingressos ativos por evento
3. `idx_orders_status_created_at` em `orders(status, created_at)` para relatórios de vendas por período
4. `idx_check_ins_event_id_check_in_time` em `check_ins(event_id, check_in_time)` para relatórios de check-in


## Funções e Triggers

### Função: update_ticket_availability

Atualiza a disponibilidade de ingressos quando um pedido é confirmado ou cancelado.

```sql
CREATE OR REPLACE FUNCTION update_ticket_availability()
RETURNS TRIGGER AS $$
BEGIN
  -- Lógica para atualizar contadores de disponibilidade
  -- baseado no status do pedido
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER order_status_change
AFTER UPDATE OF status ON orders
FOR EACH ROW
WHEN (OLD.status IS DISTINCT FROM NEW.status)
EXECUTE FUNCTION update_ticket_availability();
```

### Função: check_batch_availability

Verifica se um lote ainda está disponível antes de permitir a compra.

```sql
CREATE OR REPLACE FUNCTION check_batch_availability()
RETURNS TRIGGER AS $$
BEGIN
  -- Verifica se o lote está ativo e dentro do período
  -- Verifica se ainda há ingressos disponíveis
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER before_order_item_insert
BEFORE INSERT ON order_items
FOR EACH ROW
EXECUTE FUNCTION check_batch_availability();
```

## Considerações de Implementação

### Tipos de Dados

- Use `uuid` para identificadores primários para melhor segurança e distribuição
- Use `timestamp with time zone` para armazenar datas e horas com informação de fuso horário
- Use `json` ou `jsonb` para dados estruturados flexíveis


### Segurança

- Implemente criptografia para senhas (bcrypt ou similar)
- Use prepared statements para evitar injeção SQL
- Implemente controle de acesso baseado em papéis (RBAC)


### Performance

- Considere particionamento de tabelas para eventos muito grandes
- Implemente cache para consultas frequentes
- Monitore e otimize consultas lentas


### Backup e Recuperação

- Configure backups automáticos diários
- Teste regularmente a recuperação de backups
- Implemente point-in-time recovery


## Migrações

As migrações do banco de dados devem ser versionadas e aplicadas de forma controlada. Recomenda-se o uso de ferramentas como Prisma, TypeORM ou Knex.js para gerenciar migrações.

Exemplo de migração para criar a tabela de usuários:

```typescript
// 20230501000000_create_users_table.ts
export async function up(knex) {
  return knex.schema.createTable('users', (table) => {
    table.uuid('id').primary().defaultTo(knex.raw('uuid_generate_v4()'));
    table.string('name').notNullable();
    table.string('email').notNullable().unique();
    table.string('password').notNullable();
    table.string('phone');
    table.string('document');
    table.date('birth_date');
    table.string('profile_image');
    table.boolean('is_active').defaultTo(true);
    table.boolean('email_verified').defaultTo(false);
    table.timestamp('created_at').defaultTo(knex.fn.now());
    table.timestamp('updated_at').defaultTo(knex.fn.now());
  });
}

export async function down(knex) {
  return knex.schema.dropTable('users');
}
```

## Conclusão

Este documento fornece a estrutura completa do banco de dados para o sistema Unyx Ticket. A implementação deve seguir as melhores práticas de segurança, performance e manutenção. Recomenda-se revisões periódicas do esquema para garantir que ele continue atendendo às necessidades do negócio à medida que o sistema evolui.