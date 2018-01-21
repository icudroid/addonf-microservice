import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AgencyUser } from './agency-user.model';
import { AgencyUserService } from './agency-user.service';

@Component({
    selector: 'jhi-agency-user-detail',
    templateUrl: './agency-user-detail.component.html'
})
export class AgencyUserDetailComponent implements OnInit, OnDestroy {

    agencyUser: AgencyUser;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private agencyUserService: AgencyUserService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAgencyUsers();
    }

    load(id) {
        this.agencyUserService.find(id).subscribe((agencyUser) => {
            this.agencyUser = agencyUser;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAgencyUsers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'agencyUserListModification',
            (response) => this.load(this.agencyUser.id)
        );
    }
}
