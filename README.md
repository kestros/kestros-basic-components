# Kestros Basic Components

A set of foundational content components for the Kestros CMS, providing common building blocks for site construction.

## Purpose

`kestros-basic-components` provides the standard component types that are available on every Kestros CMS instance. These include structural and content components such as headings, text blocks, buttons, alerts, code blocks, page titles, images, and navigation elements.

This is a **reactor project** with two sub-modules:

| Module | Purpose |
|--------|---------|
| `core` | Sling Models, validation services, and component logic |
| `frontend` | Frontend assets (CSS, JS, HTL templates) for rendering |

## Installation & Build

**Maven coordinates:**

```
io.kestros.commons:kestros-basic-components
```

**Build all modules:**

```bash
mvn clean package
```

**Deploy to a Sling instance:**

```bash
mvn clean install -P installBundle -Dsling.host=localhost -Dsling.port=8080
```

## Configuration

No OSGi configuration is required. Component type definitions are installed as JCR content under `/apps/kestros/components/`.

### JCR Content Paths

| Path | Purpose |
|------|---------|
| `/apps/kestros/components/` | Component type definitions and HTL scripts |
| `/apps/kestros/components/content/` | Content components (text, heading, button, etc.) |

## API / Service Usage

### Content Components

| Component | Sling Model | Description |
|-----------|-------------|-------------|
| Alert | `AlertComponent` | Styled alert/notification message |
| Button | `Button` | Clickable button with label and link |
| Code | `CodeComponent` | Formatted code block |
| Heading | `Heading` | Section heading (H1-H6) |
| Image | `ImageComponent` | Responsive image with alt text |
| Navigation | `NavigationElement` | Navigation menu item or tree |
| Page Title | `PageTitle` | Renders the current page title |
| Text | `TextComponent` | Rich text content block |

### Validation Services

Each component with configurable properties has a validation service that ensures content is well-formed:

| Service | Validates |
|---------|-----------|
| `ButtonValidationService` | Button has a label and valid link |
| `CodeComponentValidationService` | Code block has content |
| `TextComponentValidationService` | Text block is not empty |

### Creating a New Component

1. Define the component type under `/apps/kestros/components/`
2. Create a Sling Model in `core/` that extends `BaseComponent`
3. Create HTL rendering scripts in `frontend/`
4. Optionally add a validation service

## Dependencies

**Depends on:**

| Module | Purpose |
|--------|---------|
| `kestros-cms-foundation` | Foundation services for component rendering |
| `kestros-sitebuilding-api` | `BaseComponent` base class |
| `kestros-component-types-api` | Component type interfaces |
| `kestros-ui-frameworks-api` | UI framework view integration |
| `kestros-structured-sling-models` | Base Sling Model classes |

**Depended on by:**

| Module | Purpose |
|--------|---------|
| Site implementations | Sites use these components as content building blocks |
| `kestros-design-framework` | Design system builds on basic component patterns |
