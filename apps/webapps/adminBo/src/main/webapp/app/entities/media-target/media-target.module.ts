import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdminBoSharedModule } from '../../shared';
import {
    MediaTargetService,
    MediaTargetPopupService,
    MediaTargetComponent,
    MediaTargetDetailComponent,
    MediaTargetDialogComponent,
    MediaTargetPopupComponent,
    MediaTargetDeletePopupComponent,
    MediaTargetDeleteDialogComponent,
    mediaTargetRoute,
    mediaTargetPopupRoute,
    MediaTargetResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...mediaTargetRoute,
    ...mediaTargetPopupRoute,
];

@NgModule({
    imports: [
        AdminBoSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MediaTargetComponent,
        MediaTargetDetailComponent,
        MediaTargetDialogComponent,
        MediaTargetDeleteDialogComponent,
        MediaTargetPopupComponent,
        MediaTargetDeletePopupComponent,
    ],
    entryComponents: [
        MediaTargetComponent,
        MediaTargetDialogComponent,
        MediaTargetPopupComponent,
        MediaTargetDeleteDialogComponent,
        MediaTargetDeletePopupComponent,
    ],
    providers: [
        MediaTargetService,
        MediaTargetPopupService,
        MediaTargetResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdminBoMediaTargetModule {}
