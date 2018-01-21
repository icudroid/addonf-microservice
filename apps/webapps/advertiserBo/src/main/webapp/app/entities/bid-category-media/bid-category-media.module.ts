import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdvertiserBoSharedModule } from '../../shared';
import {
    BidCategoryMediaService,
    BidCategoryMediaPopupService,
    BidCategoryMediaComponent,
    BidCategoryMediaDetailComponent,
    BidCategoryMediaDialogComponent,
    BidCategoryMediaPopupComponent,
    BidCategoryMediaDeletePopupComponent,
    BidCategoryMediaDeleteDialogComponent,
    bidCategoryMediaRoute,
    bidCategoryMediaPopupRoute,
    BidCategoryMediaResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...bidCategoryMediaRoute,
    ...bidCategoryMediaPopupRoute,
];

@NgModule({
    imports: [
        AdvertiserBoSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        BidCategoryMediaComponent,
        BidCategoryMediaDetailComponent,
        BidCategoryMediaDialogComponent,
        BidCategoryMediaDeleteDialogComponent,
        BidCategoryMediaPopupComponent,
        BidCategoryMediaDeletePopupComponent,
    ],
    entryComponents: [
        BidCategoryMediaComponent,
        BidCategoryMediaDialogComponent,
        BidCategoryMediaPopupComponent,
        BidCategoryMediaDeleteDialogComponent,
        BidCategoryMediaDeletePopupComponent,
    ],
    providers: [
        BidCategoryMediaService,
        BidCategoryMediaPopupService,
        BidCategoryMediaResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdvertiserBoBidCategoryMediaModule {}
