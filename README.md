# Kestros Basic Components

Reusable Sling components for the Kestros CMS component library.

## Component Categories

### Content Components

| Component | Datasources | Description |
|---|---|---|
| Alert | Static | Alert messages |
| Button | Static | Clickable buttons |
| Button Group | Static | Groups of buttons |
| Card | Static, Page Card, Asset Card | Card with title, image, button |
| Code | Static | Code blocks |
| Heading | Static, Page Title | Heading elements (h1-h6) |
| Image | Static, Asset Image | Image elements |
| Link | Static | Anchor links |
| Rich Text | Static | Rich text content |
| Table | Static | Table with rows, headers, cells |
| Text | Static, Rich Text | Plain text content |
| Video | Static, Asset Video | Video elements |
| Video Embed | YouTube | Embedded video (YouTube) |

### List Components

| Component | Datasources | Description |
|---|---|---|
| Card List | Static, Child Pages, Asset List, Tag Search | List of cards from various sources |
| Link List | Static, Child Pages, Tag Search | List of links from various sources |

### Navigation Components

| Component | Datasources | Description |
|---|---|---|
| Breadcrumb | Static | Single breadcrumb item |
| Breadcrumbs | Page Path | Breadcrumb trail |
| Navigation | Static | Navigation menu |
| Top Navigation | Static | Top-level navigation bar |

### Structure Components

| Component | Datasources | Description |
|---|---|---|
| Accordion | Static | Collapsible accordion panels |
| Container | Static | Generic container |
| Grid | Static | CSS grid layout |
| Section | Static | Page section wrapper |

## Dynamic List Datasources

The card-list and link-list components support dynamic datasources that generate list items from JCR content.

### Card List Datasources

- **Child Pages** (`child-pages`) -- Lists child pages of a configurable root path as cards.
- **Asset List** (`asset-list`) -- Lists assets from a configurable asset collection.
- **Tag Search** (`tag-search`) -- Finds pages matching configured tags using TagRetrievalService. Excludes the current page from results.

### Link List Datasources

- **Child Pages** (`child-pages`) -- Lists child pages of a configurable root path as links.
- **Tag Search** (`tag-search`) -- Finds pages matching configured tags using TagRetrievalService. Supports optional root path override. Excludes the current page from results.

### Sort, Reverse, and Limit Controls

All five dynamic list datasources (card-list child-pages, asset-list, tag-search; link-list child-pages, tag-search) support the following dialog controls:

| Field | Property | Type | Description |
|---|---|---|---|
| Sort By | `sortBy` | Select | None (natural order), Title, Name, Created Date, Last Modified |
| Reverse Order | `reverse` | Checkbox | Reverses the sort order |
| Limit | `limit` | Text | Maximum items to display (0 = no limit) |

Sort options use JCR child node format in the datasource JSON (not JSON arrays) to work correctly with the `@ChildResource` injection pattern used by the selectfield component.

## Datasource JSON Registration

Each datasource is registered as a JSON file under the component's `datasources/` directory:

```
src/main/resources/libs/kestros/commons/components/{category}/{component}/datasources/{name}.json
```

The JSON file must include a `classPath` property pointing to the fully-qualified Java class name of the datasource implementation.

## Building

```bash
mvn clean install -Drat.skip=true
```

## Deploying

```bash
mvn clean install -P installBundle -Dsling.host=localhost -Dsling.port=8000 -Dsling.password=<password>
```
