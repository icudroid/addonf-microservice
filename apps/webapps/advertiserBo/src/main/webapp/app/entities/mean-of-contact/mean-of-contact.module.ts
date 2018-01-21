import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdvertiserBoSharedModule } from '../../shared';
import {
    MeanOfContactService,
    MeanOfContactPopupService,
    MeanOfContactComponent,
    MeanOfContactDetailComponent,
    MeanOfContactDialogComponent,
    MeanOfContactPopupComponent,
    MeanOfContactDeletePopupComponent,
    MeanOfContactDeleteDialogComponent,
    meanOfContactRoute,
    meanOfContactPopupRoute,
    MeanOfContactResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...meanOfContactRoute,
    ...meanOfContactPopupRoute,
];

@NgModule({
    imports: [
        AdvertiserBoSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MeanOfContactComponent,
        MeanOfContactDetailComponent,
        MeanOfContactDialogComponent,
        MeanOfContactDeleteDialogComponent,
        MeanOfContactPopupComponent,
        MeanOfContactDeletePopupComponent,
    ],
    entryComponents: [
        MeanOfContactComponent,
        MeanOfContactDialogComponent,
        MeanOfContactPopupComponent,
        MeanOfContactDeleteDialogComponent,
        MeanOfContactDeletePopupComponent,
    ],
    providers: [
        MeanOfContactService,
        MeanOfContactPopupService,
        MeanOfContactResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdvertiserBoMeanOfContactModule {}
