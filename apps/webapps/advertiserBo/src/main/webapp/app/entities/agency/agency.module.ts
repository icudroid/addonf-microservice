import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdvertiserBoSharedModule } from '../../shared';
import {
    AgencyService,
    AgencyPopupService,
    AgencyComponent,
    AgencyDetailComponent,
    AgencyDialogComponent,
    AgencyPopupComponent,
    AgencyDeletePopupComponent,
    AgencyDeleteDialogComponent,
    agencyRoute,
    agencyPopupRoute,
    AgencyResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...agencyRoute,
    ...agencyPopupRoute,
];

@NgModule({
    imports: [
        AdvertiserBoSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AgencyComponent,
        AgencyDetailComponent,
        AgencyDialogComponent,
        AgencyDeleteDialogComponent,
        AgencyPopupComponent,
        AgencyDeletePopupComponent,
    ],
    entryComponents: [
        AgencyComponent,
        AgencyDialogComponent,
        AgencyPopupComponent,
        AgencyDeleteDialogComponent,
        AgencyDeletePopupComponent,
    ],
    providers: [
        AgencyService,
        AgencyPopupService,
        AgencyResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdvertiserBoAgencyModule {}
