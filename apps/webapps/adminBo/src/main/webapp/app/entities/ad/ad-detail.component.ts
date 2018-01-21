import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Ad } from './ad.model';
import { AdService } from './ad.service';

@Component({
    selector: 'jhi-ad-detail',
    templateUrl: './ad-detail.component.html'
})
export class AdDetailComponent implements OnInit, OnDestroy {

    ad: Ad;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private adService: AdService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAds();
    }

    load(id) {
        this.adService.find(id).subscribe((ad) => {
            this.ad = ad;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAds() {
        this.eventSubscriber = this.eventManager.subscribe(
            'adListModification',
            (response) => this.load(this.ad.id)
        );
    }
}
