import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdvertiserBoSharedModule } from '../../shared';
import {
    AgencyUserService,
    AgencyUserPopupService,
    AgencyUserComponent,
    AgencyUserDetailComponent,
    AgencyUserDialogComponent,
    AgencyUserPopupComponent,
    AgencyUserDeletePopupComponent,
    AgencyUserDeleteDialogComponent,
    agencyUserRoute,
    agencyUserPopupRoute,
    AgencyUserResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...agencyUserRoute,
    ...agencyUserPopupRoute,
];

@NgModule({
    imports: [
        AdvertiserBoSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AgencyUserComponent,
        AgencyUserDetailComponent,
        AgencyUserDialogComponent,
        AgencyUserDeleteDialogComponent,
        AgencyUserPopupComponent,
        AgencyUserDeletePopupComponent,
    ],
    entryComponents: [
        AgencyUserComponent,
        AgencyUserDialogComponent,
        AgencyUserPopupComponent,
        AgencyUserDeleteDialogComponent,
        AgencyUserDeletePopupComponent,
    ],
    providers: [
        AgencyUserService,
        AgencyUserPopupService,
        AgencyUserResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdvertiserBoAgencyUserModule {}
