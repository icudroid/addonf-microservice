import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdminBoSharedModule } from '../../shared';
import {
    BrandUserService,
    BrandUserPopupService,
    BrandUserComponent,
    BrandUserDetailComponent,
    BrandUserDialogComponent,
    BrandUserPopupComponent,
    BrandUserDeletePopupComponent,
    BrandUserDeleteDialogComponent,
    brandUserRoute,
    brandUserPopupRoute,
    BrandUserResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...brandUserRoute,
    ...brandUserPopupRoute,
];

@NgModule({
    imports: [
        AdminBoSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        BrandUserComponent,
        BrandUserDetailComponent,
        BrandUserDialogComponent,
        BrandUserDeleteDialogComponent,
        BrandUserPopupComponent,
        BrandUserDeletePopupComponent,
    ],
    entryComponents: [
        BrandUserComponent,
        BrandUserDialogComponent,
        BrandUserPopupComponent,
        BrandUserDeleteDialogComponent,
        BrandUserDeletePopupComponent,
    ],
    providers: [
        BrandUserService,
        BrandUserPopupService,
        BrandUserResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdminBoBrandUserModule {}
