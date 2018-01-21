import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AdCampaing } from './ad-campaing.model';
import { AdCampaingService } from './ad-campaing.service';

@Component({
    selector: 'jhi-ad-campaing-detail',
    templateUrl: './ad-campaing-detail.component.html'
})
export class AdCampaingDetailComponent implements OnInit, OnDestroy {

    adCampaing: AdCampaing;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private adCampaingService: AdCampaingService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAdCampaings();
    }

    load(id) {
        this.adCampaingService.find(id).subscribe((adCampaing) => {
            this.adCampaing = adCampaing;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAdCampaings() {
        this.eventSubscriber = this.eventManager.subscribe(
            'adCampaingListModification',
            (response) => this.load(this.adCampaing.id)
        );
    }
}
