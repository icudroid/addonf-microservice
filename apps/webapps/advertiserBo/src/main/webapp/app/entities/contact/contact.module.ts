import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdvertiserBoSharedModule } from '../../shared';
import {
    ContactService,
    ContactPopupService,
    ContactComponent,
    ContactDetailComponent,
    ContactDialogComponent,
    ContactPopupComponent,
    ContactDeletePopupComponent,
    ContactDeleteDialogComponent,
    contactRoute,
    contactPopupRoute,
    ContactResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...contactRoute,
    ...contactPopupRoute,
];

@NgModule({
    imports: [
        AdvertiserBoSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ContactComponent,
        ContactDetailComponent,
        ContactDialogComponent,
        ContactDeleteDialogComponent,
        ContactPopupComponent,
        ContactDeletePopupComponent,
    ],
    entryComponents: [
        ContactComponent,
        ContactDialogComponent,
        ContactPopupComponent,
        ContactDeleteDialogComponent,
        ContactDeletePopupComponent,
    ],
    providers: [
        ContactService,
        ContactPopupService,
        ContactResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdvertiserBoContactModule {}
