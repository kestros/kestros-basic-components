# Kestros Basic Components

Reusable Sling components for the Kestros CMS component library.

## Component List

## Component Categories

### Content Components

| Component | Datasource Variants |
|---|---|
| Alert | default |
| Button | default |
| Button Group | default |
| Card | default, asset-card, page-card |
| Code | default |
| Heading | default, page-title |
| Image | default, asset-image |
| Link | default |
| Rich Text | default |
| Table | default |
| Table Cell | default |
| Table Header | default |
| Table Row | default |
| Text | default, richtext |
| Video | default, asset-video |
| Video Embed | default |

### List Components

| Component | Datasource Variants |
|---|---|
| Card List | default, child-pages, asset-list, tag-search |
| Link List | default, child-pages, tag-search |

### Navigation Components

| Component | Datasource Variants |
|---|---|
| Breadcrumb | default |
| Breadcrumbs | default |
| Navigation | default |
| Navigation Item | default |
| Top Navigation | default |
| Top Navigation Item | default |

### Structure Components

| Component | Datasource Variants |
|---|---|
| Accordion | default |
| Accordion Panel | default |
| Container | default |
| Grid | default |
| Section | default |

## Datasource Architecture

Each component has one or more datasource variants defined in:
`src/main/resources/libs/kestros/commons/components/{category}/{component}/datasources/{variant}.json`

Each JSON file declares a `classPath` pointing to a Java class that extends `BaseSlingModelDataSource` (or `BaseContainerSlingModelDataSource` for list/container types).

### Tag Search Datasources

Card List and Link List support tag-based page search via `kestros-tagging-api`:
- `CardListTagSearchDataSource` - finds pages matching configured tags and renders as card elements
- `LinkListTagSearchDataSource` - finds pages matching configured tags and renders as link elements

Both use `TagRetrievalService` for tag lookup and support sort, reverse, and limit options.
