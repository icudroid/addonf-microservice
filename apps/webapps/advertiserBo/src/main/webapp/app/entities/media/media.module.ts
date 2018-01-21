import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdvertiserBoSharedModule } from '../../shared';
import {
    MediaService,
    MediaPopupService,
    MediaComponent,
    MediaDetailComponent,
    MediaDialogComponent,
    MediaPopupComponent,
    MediaDeletePopupComponent,
    MediaDeleteDialogComponent,
    mediaRoute,
    mediaPopupRoute,
    MediaResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...mediaRoute,
    ...mediaPopupRoute,
];

@NgModule({
    imports: [
        AdvertiserBoSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MediaComponent,
        MediaDetailComponent,
        MediaDialogComponent,
        MediaDeleteDialogComponent,
        MediaPopupComponent,
        MediaDeletePopupComponent,
    ],
    entryComponents: [
        MediaComponent,
        MediaDialogComponent,
        MediaPopupComponent,
        MediaDeleteDialogComponent,
        MediaDeletePopupComponent,
    ],
    providers: [
        MediaService,
        MediaPopupService,
        MediaResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdvertiserBoMediaModule {}
