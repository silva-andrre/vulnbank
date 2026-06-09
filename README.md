# VulnBank 🏦

> **⚠️ AVISO LEGAL:** Este aplicativo foi criado **exclusivamente para fins educacionais**. Todas as vulnerabilidades foram implementadas intencionalmente para demonstrar os riscos do OWASP Mobile Top 10. **Nunca utilize este código em produção.**

---

## O que é o VulnBank?

O VulnBank é um aplicativo Android bancário intencionalmente vulnerável, desenvolvido como material de estudo prático para o **OWASP Mobile Top 10**. Ele foi criado como *companion* da série de artigos **"VulnBank — Uma Abordagem Prática de Mobile Security"**, publicada no Medium.

O projeto cobre o ciclo completo de segurança mobile:
- **Implementação** das vulnerabilidades com código comentado
- **Exploração** de cada falha com ferramentas profissionais
- **Correção** com explicação técnica, de AppSec e de negócio

---

## 📚 Série de Artigos

| Parte | Título | Link |
|---|---|---|
| 1 | O Estudo — Como posso aprender a teoria de forma prática? | [Medium](https://medium.com/@andrre/vulnbank-uma-abordagem-pr%C3%A1tica-de-mobile-security-a0685119856f) |
| 2 | Construindo o Laboratório | [Medium](https://medium.com/@andrre/vulnbank-uma-abordagem-pr%C3%A1tica-de-mobile-security-pt-2-ff9e51089642) |
| 3 | Criando as Vulnerabilidades | [Medium](https://medium.com/@andrre/vulnbank-uma-abordagem-pr%C3%A1tica-de-mobile-security-pt-3-dda8783f3429) |
| 4 | Explorando como um Atacante | [Medium](https://medium.com/@andrre/vulnbank-uma-abordagem-pr%C3%A1tica-de-mobile-security-pt-4-ddb3dda38eb4) |
| 5 | Corrigindo como um AppSec | [Medium](https://medium.com/@andrre/vulnbank-uma-abordagem-pr%C3%A1tica-de-mobile-security-pt-5-920806d4f0d8) |
| 6 | Percepções e Aprendizados | [Medium](https://medium.com/@andrre/vulnbank-uma-abordagem-pr%C3%A1tica-de-mobile-security-pt-6-e3d4a87ffd87) |

> Substitua os links `[Medium](#)` pelos URLs reais após a publicação.

---

## 🔟 Vulnerabilidades Implementadas

| # | OWASP Mobile Top 10 | Localização no código |
|---|---|---|
| M1 | Improper Credential Usage | `LoginActivity.kt` |
| M2 | Inadequate Supply Chain Security | `build.gradle.kts` |
| M3 | Insecure Authentication/Authorization | `TransferActivity.kt` |
| M4 | Insufficient Input/Output Validation | `TransferActivity.kt` |
| M5 | Insecure Communication | `TransferActivity.kt` + `AndroidManifest.xml` |
| M6 | Inadequate Privacy Controls | Todas as Activities |
| M7 | Insufficient Binary Protections | `build.gradle.kts` + `AndroidManifest.xml` |
| M8 | Security Misconfiguration | `AndroidManifest.xml` + `ProfileActivity.kt` |
| M9 | Insecure Data Storage | `LoginActivity.kt` + `DashboardActivity.kt` |
| M10 | Insufficient Cryptography | `LoginActivity.kt` |

---

## 🏗️ Estrutura do Projeto

```
VulnBank/
├── app/
│   └── src/main/
│       ├── java/com/vulnbank/app/
│       │   ├── LoginActivity.kt       ← M1, M6, M9, M10
│       │   ├── DashboardActivity.kt   ← M6, M9
│       │   ├── TransferActivity.kt    ← M3, M4, M5, M6
│       │   └── ProfileActivity.kt     ← M6, M8
│       ├── res/layout/
│       └── AndroidManifest.xml        ← M5, M7, M8
├── gradle/
│   └── libs.versions.toml             ← M2
└── app/build.gradle.kts               ← M2, M7
```

---

## 🛠️ Pré-requisitos

- Android Studio (qualquer versão recente)
- JDK 11+
- Emulador Android com imagem **Google APIs** (sem Google Play) — necessário para ferramentas de pentest
- Recomendado: Pixel 6, API 31

---

## 🚀 Como usar

### 1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/vulnbank.git
cd vulnbank
```

### 2. Abra no Android Studio

- File → Open → selecione a pasta `vulnbank`
- Aguarde o Gradle sync

### 3. Configure o emulador

No Device Manager do Android Studio:
- Device: Pixel 6
- System Image: **Google APIs, API 31** (sem Google Play)
- ABI: x86_64

### 4. Execute o app

- Clique em **Run ▶**
- Credenciais de acesso: `admin / admin123`

---

## 🔧 Ferramentas de Exploração

| Ferramenta | Uso | Download |
|---|---|---|
| ADB | Logs e acesso ao filesystem | Incluído no Android Studio |
| jadx | Engenharia reversa do APK | [github.com/skylot/jadx](https://github.com/skylot/jadx) |
| Burp Suite | Interceptação de tráfego HTTP | [portswigger.net](https://portswigger.net/burp/communitydownload) |
| Frida | Instrumentação dinâmica | [frida.re](https://frida.re) |
| OWASP Dependency-Check | Análise de supply chain | [jeremylong.github.io](https://jeremylong.github.io/DependencyCheck/) |

---

## 🔒 Branch: versão corrigida

Este repositório possui duas branches:

| Branch | Descrição |
|---|---|
| `main` | App vulnerável — para estudo e exploração |
| `fixed` | App com todas as correções aplicadas |

Para comparar as diferenças:

```bash
git diff main fixed
```

---

## ⚠️ Disclaimer

Este projeto foi desenvolvido **exclusivamente para fins educacionais e de pesquisa em segurança**. O uso das técnicas demonstradas contra sistemas sem autorização explícita é **ilegal** e **antiético**.

O autor não se responsabiliza pelo uso indevido deste material. Utilize apenas em ambientes controlados de laboratório.

---

## 📄 Licença

[MIT License](LICENSE) — livre para uso educacional com atribuição.

---

## 👤 Autor

**André Nascimento**
- Medium: [@andrre](https://medium.com/@andrre)
- LinkedIn: [linkedin.com/in/nascimento-andrre](https://www.linkedin.com/in/nascimento-andrre/)

---

*Desenvolvido com 🤖 [Claude](https://www.anthropic.com) como parceiro de estudo.*
