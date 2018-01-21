/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { AdvertiserBoTestModule } from '../../../test.module';
import { AdCampaingComponent } from '../../../../../../main/webapp/app/entities/ad-campaing/ad-campaing.component';
import { AdCampaingService } from '../../../../../../main/webapp/app/entities/ad-campaing/ad-campaing.service';
import { AdCampaing } from '../../../../../../main/webapp/app/entities/ad-campaing/ad-campaing.model';

describe('Component Tests', () => {

    describe('AdCampaing Management Component', () => {
        let comp: AdCampaingComponent;
        let fixture: ComponentFixture<AdCampaingComponent>;
        let service: AdCampaingService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdvertiserBoTestModule],
                declarations: [AdCampaingComponent],
                providers: [
                    AdCampaingService
                ]
            })
            .overrideTemplate(AdCampaingComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AdCampaingComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdCampaingService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new AdCampaing(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.adCampaings[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
