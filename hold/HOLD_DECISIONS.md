# Hold Directory — Deferred Components

Components in this directory are not yet ready for integration into the main component tree.

## Tabs Components (KestrosTabs, KestrosTab, KestrosTabHeader, KestrosTabContent)

**Status**: Deferred — do not integrate until blockers are resolved.

**Reasons**:

1. **Invalid resource types**: `KestrosTabs.RESOURCE_TYPE` is set to `/hold/tabs` — a placeholder path. Integration requires renaming all resource types to real component paths under `kestros/commons/components/`.

2. **No JavaScript layer**: The tab components would render as unstyled, non-functional markup without a JavaScript interaction layer. No JS is implemented.

3. **Non-standard constructor pattern**: `KestrosTabsImpl` accepts tabs as a constructor parameter rather than discovering child resources from JCR. Every other container component in the codebase (`KestrosAccordionImpl`, `KestrosCardListImpl`, etc.) discovers children from JCR. This design needs to be aligned before deployment.

4. **No unit tests**: No test classes exist for any tab component.

**Blocked by**: A follow-on task must be created to redesign and integrate the tabs system. See TASK-007 notes for context.
