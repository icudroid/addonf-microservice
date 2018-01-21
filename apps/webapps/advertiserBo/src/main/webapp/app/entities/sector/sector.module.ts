import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdvertiserBoSharedModule } from '../../shared';
import {
    SectorService,
    SectorPopupService,
    SectorComponent,
    SectorDetailComponent,
    SectorDialogComponent,
    SectorPopupComponent,
    SectorDeletePopupComponent,
    SectorDeleteDialogComponent,
    sectorRoute,
    sectorPopupRoute,
    SectorResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...sectorRoute,
    ...sectorPopupRoute,
];

@NgModule({
    imports: [
        AdvertiserBoSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SectorComponent,
        SectorDetailComponent,
        SectorDialogComponent,
        SectorDeleteDialogComponent,
        SectorPopupComponent,
        SectorDeletePopupComponent,
    ],
    entryComponents: [
        SectorComponent,
        SectorDialogComponent,
        SectorPopupComponent,
        SectorDeleteDialogComponent,
        SectorDeletePopupComponent,
    ],
    providers: [
        SectorService,
        SectorPopupService,
        SectorResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdvertiserBoSectorModule {}
