# Stage 1 - API Design

Base URL:

```text
/api/v1
```

Endpoints:

```http
GET /notifications
GET /notifications/{id}
POST /notifications
PUT /notifications/{id}/read
DELETE /notifications/{id}
```

Real-time notifications can be implemented using WebSocket.

---

# Stage 2 - Database Design

Database: PostgreSQL

### Student Table

| Column | Type      |
| ------ | --------- |
| id     | BIGSERIAL |
| name   | VARCHAR   |
| email  | VARCHAR   |

### Notification Table

| Column     | Type      |
| ---------- | --------- |
| id         | BIGSERIAL |
| student_id | BIGINT    |
| title      | VARCHAR   |
| message    | TEXT      |
| type       | VARCHAR   |
| is_read    | BOOLEAN   |
| created_at | TIMESTAMP |

Example Query:

```sql
SELECT *
FROM notifications
ORDER BY created_at DESC;
```

For large data, indexing and pagination can improve performance.

---

# Stage 3 - Query Optimization

Query:

```sql
SELECT *
FROM notifications
WHERE studentID = 1042
AND isRead = false
ORDER BY createdAt DESC;
```

Recommended Index:

```sql
CREATE INDEX idx_student_read_created
ON notifications(studentID,isRead,createdAt DESC);
```

Without Index:

```text
O(n)
```

With Index:

```text
O(log n)
```

---

# Stage 4 - Performance Improvement

Possible improvements:

* Redis Cache
* Pagination
* WebSocket
* Read Replicas

These reduce database load and improve response time.

---

# Stage 5 - Reliable Delivery

Current issues:

* Slow email processing
* No retry mechanism

Better approach:

1. Save notification in DB.
2. Add task to queue.
3. Worker sends email.
4. Retry failed notifications.

This improves reliability and scalability.

---

# Stage 6 - Priority Inbox

Priority:

```text
Placement = 3
Result = 2
Event = 1
```

Notifications are sorted by:

1. Priority
2. Latest Time

For Top 10 notifications, a Priority Queue (Min Heap) can be used for efficient performance.
