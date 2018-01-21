import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdminBoSharedModule } from '../../shared';
import {
    MediaUserService,
    MediaUserPopupService,
    MediaUserComponent,
    MediaUserDetailComponent,
    MediaUserDialogComponent,
    MediaUserPopupComponent,
    MediaUserDeletePopupComponent,
    MediaUserDeleteDialogComponent,
    mediaUserRoute,
    mediaUserPopupRoute,
    MediaUserResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...mediaUserRoute,
    ...mediaUserPopupRoute,
];

@NgModule({
    imports: [
        AdminBoSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MediaUserComponent,
        MediaUserDetailComponent,
        MediaUserDialogComponent,
        MediaUserDeleteDialogComponent,
        MediaUserPopupComponent,
        MediaUserDeletePopupComponent,
    ],
    entryComponents: [
        MediaUserComponent,
        MediaUserDialogComponent,
        MediaUserPopupComponent,
        MediaUserDeleteDialogComponent,
        MediaUserDeletePopupComponent,
    ],
    providers: [
        MediaUserService,
        MediaUserPopupService,
        MediaUserResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdminBoMediaUserModule {}
