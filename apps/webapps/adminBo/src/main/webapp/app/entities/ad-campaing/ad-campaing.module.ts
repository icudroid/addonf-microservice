import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdminBoSharedModule } from '../../shared';
import {
    AdCampaingService,
    AdCampaingPopupService,
    AdCampaingComponent,
    AdCampaingDetailComponent,
    AdCampaingDialogComponent,
    AdCampaingPopupComponent,
    AdCampaingDeletePopupComponent,
    AdCampaingDeleteDialogComponent,
    adCampaingRoute,
    adCampaingPopupRoute,
    AdCampaingResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...adCampaingRoute,
    ...adCampaingPopupRoute,
];

@NgModule({
    imports: [
        AdminBoSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AdCampaingComponent,
        AdCampaingDetailComponent,
        AdCampaingDialogComponent,
        AdCampaingDeleteDialogComponent,
        AdCampaingPopupComponent,
        AdCampaingDeletePopupComponent,
    ],
    entryComponents: [
        AdCampaingComponent,
        AdCampaingDialogComponent,
        AdCampaingPopupComponent,
        AdCampaingDeleteDialogComponent,
        AdCampaingDeletePopupComponent,
    ],
    providers: [
        AdCampaingService,
        AdCampaingPopupService,
        AdCampaingResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdminBoAdCampaingModule {}
