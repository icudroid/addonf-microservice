import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdminBoSharedModule } from '../../shared';
import {
    AdService,
    AdPopupService,
    AdComponent,
    AdDetailComponent,
    AdDialogComponent,
    AdPopupComponent,
    AdDeletePopupComponent,
    AdDeleteDialogComponent,
    adRoute,
    adPopupRoute,
    AdResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...adRoute,
    ...adPopupRoute,
];

@NgModule({
    imports: [
        AdminBoSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AdComponent,
        AdDetailComponent,
        AdDialogComponent,
        AdDeleteDialogComponent,
        AdPopupComponent,
        AdDeletePopupComponent,
    ],
    entryComponents: [
        AdComponent,
        AdDialogComponent,
        AdPopupComponent,
        AdDeleteDialogComponent,
        AdDeletePopupComponent,
    ],
    providers: [
        AdService,
        AdPopupService,
        AdResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdminBoAdModule {}
