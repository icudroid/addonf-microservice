import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { CustomerTarget } from './customer-target.model';
import { CustomerTargetService } from './customer-target.service';

@Component({
    selector: 'jhi-customer-target-detail',
    templateUrl: './customer-target-detail.component.html'
})
export class CustomerTargetDetailComponent implements OnInit, OnDestroy {

    customerTarget: CustomerTarget;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private customerTargetService: CustomerTargetService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCustomerTargets();
    }

    load(id) {
        this.customerTargetService.find(id).subscribe((customerTarget) => {
            this.customerTarget = customerTarget;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCustomerTargets() {
        this.eventSubscriber = this.eventManager.subscribe(
            'customerTargetListModification',
            (response) => this.load(this.customerTarget.id)
        );
    }
}
