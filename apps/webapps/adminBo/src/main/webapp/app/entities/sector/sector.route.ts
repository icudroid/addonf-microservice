import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { SectorComponent } from './sector.component';
import { SectorDetailComponent } from './sector-detail.component';
import { SectorPopupComponent } from './sector-dialog.component';
import { SectorDeletePopupComponent } from './sector-delete-dialog.component';

@Injectable()
export class SectorResolvePagingParams implements Resolve<any> {

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

export const sectorRoute: Routes = [
    {
        path: 'sector',
        component: SectorComponent,
        resolve: {
            'pagingParams': SectorResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.sector.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'sector/:id',
        component: SectorDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.sector.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sectorPopupRoute: Routes = [
    {
        path: 'sector-new',
        component: SectorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.sector.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sector/:id/edit',
        component: SectorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.sector.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sector/:id/delete',
        component: SectorDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.sector.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
