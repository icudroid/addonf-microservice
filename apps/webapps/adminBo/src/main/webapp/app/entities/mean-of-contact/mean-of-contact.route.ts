import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { MeanOfContactComponent } from './mean-of-contact.component';
import { MeanOfContactDetailComponent } from './mean-of-contact-detail.component';
import { MeanOfContactPopupComponent } from './mean-of-contact-dialog.component';
import { MeanOfContactDeletePopupComponent } from './mean-of-contact-delete-dialog.component';

@Injectable()
export class MeanOfContactResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const meanOfContactRoute: Routes = [
    {
        path: 'mean-of-contact',
        component: MeanOfContactComponent,
        resolve: {
            'pagingParams': MeanOfContactResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.meanOfContact.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'mean-of-contact/:id',
        component: MeanOfContactDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.meanOfContact.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const meanOfContactPopupRoute: Routes = [
    {
        path: 'mean-of-contact-new',
        component: MeanOfContactPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.meanOfContact.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mean-of-contact/:id/edit',
        component: MeanOfContactPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.meanOfContact.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mean-of-contact/:id/delete',
        component: MeanOfContactDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.meanOfContact.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
