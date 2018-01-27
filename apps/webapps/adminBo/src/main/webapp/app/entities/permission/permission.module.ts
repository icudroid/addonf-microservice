import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdminBoSharedModule } from '../../shared';
import {
    PermissionService,
    PermissionPopupService,
    PermissionComponent,
    PermissionDetailComponent,
    PermissionDialogComponent,
    PermissionPopupComponent,
    PermissionDeletePopupComponent,
    PermissionDeleteDialogComponent,
    permissionRoute,
    permissionPopupRoute,
    PermissionResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...permissionRoute,
    ...permissionPopupRoute,
];

@NgModule({
    imports: [
        AdminBoSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PermissionComponent,
        PermissionDetailComponent,
        PermissionDialogComponent,
        PermissionDeleteDialogComponent,
        PermissionPopupComponent,
        PermissionDeletePopupComponent,
    ],
    entryComponents: [
        PermissionComponent,
        PermissionDialogComponent,
        PermissionPopupComponent,
        PermissionDeleteDialogComponent,
        PermissionDeletePopupComponent,
    ],
    providers: [
        PermissionService,
        PermissionPopupService,
        PermissionResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdminBoPermissionModule {}
