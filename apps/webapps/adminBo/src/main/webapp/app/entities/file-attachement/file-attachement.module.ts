import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdminBoSharedModule } from '../../shared';
import {
    FileAttachementService,
    FileAttachementPopupService,
    FileAttachementComponent,
    FileAttachementDetailComponent,
    FileAttachementDialogComponent,
    FileAttachementPopupComponent,
    FileAttachementDeletePopupComponent,
    FileAttachementDeleteDialogComponent,
    fileAttachementRoute,
    fileAttachementPopupRoute,
    FileAttachementResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...fileAttachementRoute,
    ...fileAttachementPopupRoute,
];

@NgModule({
    imports: [
        AdminBoSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        FileAttachementComponent,
        FileAttachementDetailComponent,
        FileAttachementDialogComponent,
        FileAttachementDeleteDialogComponent,
        FileAttachementPopupComponent,
        FileAttachementDeletePopupComponent,
    ],
    entryComponents: [
        FileAttachementComponent,
        FileAttachementDialogComponent,
        FileAttachementPopupComponent,
        FileAttachementDeleteDialogComponent,
        FileAttachementDeletePopupComponent,
    ],
    providers: [
        FileAttachementService,
        FileAttachementPopupService,
        FileAttachementResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdminBoFileAttachementModule {}
