import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdvertiserBoSharedModule } from '../../shared';
import {
    CustomerTargetService,
    CustomerTargetPopupService,
    CustomerTargetComponent,
    CustomerTargetDetailComponent,
    CustomerTargetDialogComponent,
    CustomerTargetPopupComponent,
    CustomerTargetDeletePopupComponent,
    CustomerTargetDeleteDialogComponent,
    customerTargetRoute,
    customerTargetPopupRoute,
    CustomerTargetResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...customerTargetRoute,
    ...customerTargetPopupRoute,
];

@NgModule({
    imports: [
        AdvertiserBoSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CustomerTargetComponent,
        CustomerTargetDetailComponent,
        CustomerTargetDialogComponent,
        CustomerTargetDeleteDialogComponent,
        CustomerTargetPopupComponent,
        CustomerTargetDeletePopupComponent,
    ],
    entryComponents: [
        CustomerTargetComponent,
        CustomerTargetDialogComponent,
        CustomerTargetPopupComponent,
        CustomerTargetDeleteDialogComponent,
        CustomerTargetDeletePopupComponent,
    ],
    providers: [
        CustomerTargetService,
        CustomerTargetPopupService,
        CustomerTargetResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdvertiserBoCustomerTargetModule {}
