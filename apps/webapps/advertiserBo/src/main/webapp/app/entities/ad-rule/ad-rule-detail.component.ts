import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AdRule } from './ad-rule.model';
import { AdRuleService } from './ad-rule.service';

@Component({
    selector: 'jhi-ad-rule-detail',
    templateUrl: './ad-rule-detail.component.html'
})
export class AdRuleDetailComponent implements OnInit, OnDestroy {

    adRule: AdRule;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private adRuleService: AdRuleService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAdRules();
    }

    load(id) {
        this.adRuleService.find(id).subscribe((adRule) => {
            this.adRule = adRule;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAdRules() {
        this.eventSubscriber = this.eventManager.subscribe(
            'adRuleListModification',
            (response) => this.load(this.adRule.id)
        );
    }
}
