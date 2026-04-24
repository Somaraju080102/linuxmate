# 🌉 LinuxMate

> **Grafana shows you the problem. LinuxMate lets you fix it.**

A secure, self-hosted web control panel to monitor and manage Linux servers remotely — without touching a terminal.

Most monitoring tools are read-only. They show you what's wrong but leave you SSHing in to actually fix it. LinuxMate closes that gap — giving developers and teams real write access to their servers through a clean, modern browser UI.

---

## 🚀 What Makes LinuxMate Different

| Tool | Monitor | Act |
|------|---------|-----|
| Grafana | ✅ | ❌ |
| Netdata | ✅ | ❌ |
| Prometheus | ✅ | ❌ |
| **LinuxMate** | ✅ | ✅ |

---

## ✨ Features

### 🔍 Observe (Phase 2 — Complete)
- **System Stats** — Real-time CPU, RAM, and disk usage
- **Uptime** — Server uptime at a glance
- **Smart Alerts** — Automatic warnings when disk > 80% or memory > 75%
- **Process Monitor** — View all running processes with PID, user, CPU, memory and command
- **Service Status** — List all systemd services and their current state
- **Log Viewer** — Tail logs directly from `/var/log`
- **File Browser** — Navigate the server file system from your browser
- **Cron Jobs** — View all scheduled cron tasks
- **User List** — See all system users
- **Disk Partitions** — View all mount points and partition usage

### ⚡ Act (Phase 3 — Coming Soon)
- Kill processes, restart services, edit and upload files
- Create and delete users, manage groups
- Control firewall rules via `ufw`
- Install and remove packages via `apt`
- Add and delete cron jobs
- Run sandboxed custom commands (ADMIN only)

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-----------|
| Backend | Spring Boot (Java) |
| Auth | Spring Security + JWT + Refresh Tokens |
| Frontend | React + Vite |
| Charts | Recharts |
| Real-time | WebSocket (live log streaming) |
| Database | H2 (audit logs) |
| File Upload | Apache Commons IO |

---

## 🔐 Security

- **Role-based access control** — ADMIN (full access) vs VIEWER (read-only)
- **JWT authentication** with refresh token support
- **Audit logging** — every write action is logged with user and timestamp
- **Command execution wrapper** — no raw shell injection, all commands go through a controlled `ProcessBuilder` layer
- **Confirm dialogs** on all destructive actions

---

## 📡 API Reference

### System
```
GET  /api/system/stats        → CPU, RAM, disk usage (structured JSON)
GET  /api/system/uptime       → Server uptime
GET  /api/system/alerts       → Active alerts (disk/memory thresholds)
```

### Processes
```
GET    /api/processes         → All running processes
DELETE /api/processes/{pid}   → Kill a process (ADMIN only)
```

### Services
```
GET  /api/services                      → List all systemd services
POST /api/services/{name}/start         → Start a service
POST /api/services/{name}/stop          → Stop a service
POST /api/services/{name}/restart       → Restart a service
```

### Logs
```
GET /api/logs?file=syslog     → Tail a log file
WS  /ws/logs                  → Live log streaming via WebSocket
```

### Files
```
GET    /api/files?path=/home  → List directory contents
PUT    /api/files/edit        → Edit a file
POST   /api/files/upload      → Upload a file
DELETE /api/files/delete      → Delete a file
```

### Cron
```
GET    /api/cron              → List all cron jobs
POST   /api/cron              → Add a cron job
DELETE /api/cron/{id}         → Remove a cron job
```

### Users
```
GET    /api/users                          → List all system users
POST   /api/users/create                   → Create a user
DELETE /api/users/{username}               → Delete a user
POST   /api/users/{username}/password      → Change password
POST   /api/users/{username}/group         → Add user to group
```

### Disk
```
GET /api/disk/partitions      → All mount points and partition usage
```

### Firewall
```
POST /api/firewall/enable         → Enable ufw
POST /api/firewall/disable        → Disable ufw
POST /api/firewall/port/open      → Open a port
POST /api/firewall/port/close     → Close a port
```

### Packages
```
POST /api/packages/install    → Install a package via apt
POST /api/packages/remove     → Remove a package via apt
```

---

## ⚙️ Getting Started

### Prerequisites
- Java 17+
- Node.js 18+
- Linux server (Ubuntu/Debian recommended)

### Backend
```bash
git clone https://github.com/yourusername/linuxmate.git
cd linuxmate/linuxmate-server
./mvnw spring-boot:run
```

### Frontend
```bash
cd linuxmate/linuxmate-ui
npm install
npm run dev
```

### Default Login
```
Username: admin
Password: admin123
```
> ⚠️ Change these immediately in `application.properties` before exposing to any network.

---

## 🗂️ Project Structure

```
linuxmate/
├── linxmate-server/          # Spring Boot backend
│   └── src/main/java/
│       ├── auth/                # JWT, roles, security config
│       ├── system/              # Stats, uptime, alerts
│       ├── process/             # Process management
│       ├── service/             # Systemd service control
│       ├── logs/                # Log viewer + WebSocket
│       ├── files/               # File browser and editor
│       ├── cron/                # Cron job management
│       ├── users/               # User management
│       ├── firewall/            # UFW control
│       ├── packages/            # APT package management
│       └── audit/               # Audit log
│
└── linuxmate-ui/              # React + Vite frontend
    └── src/
        ├── pages/               # Dashboard, Processes, Services...
        ├── components/          # Charts, tables, dialogs
        └── api/                 # Axios API layer
```

---

## 🗺️ Roadmap

- [x] Phase 1 — Auth, JWT, project setup
- [x] Phase 2 — Read APIs (observe)
- [ ] Phase 3 — Write APIs (act)
- [ ] Phase 4 — Frontend dashboard + all pages
- [ ] Phase 5 — Polish, dark mode, WebSocket live logs
- [ ] Phase 6 — Multi-server support (connect via IP)

---

## 🤝 Built By

Built by a Linux admin who understands both sides — the server and the developer who fears it.

---

## 📄 License

MIT License — free to use, self-host, and modify.
