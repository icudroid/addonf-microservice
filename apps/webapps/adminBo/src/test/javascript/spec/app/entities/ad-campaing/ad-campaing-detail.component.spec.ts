/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { AdminBoTestModule } from '../../../test.module';
import { AdCampaingDetailComponent } from '../../../../../../main/webapp/app/entities/ad-campaing/ad-campaing-detail.component';
import { AdCampaingService } from '../../../../../../main/webapp/app/entities/ad-campaing/ad-campaing.service';
import { AdCampaing } from '../../../../../../main/webapp/app/entities/ad-campaing/ad-campaing.model';

describe('Component Tests', () => {

    describe('AdCampaing Management Detail Component', () => {
        let comp: AdCampaingDetailComponent;
        let fixture: ComponentFixture<AdCampaingDetailComponent>;
        let service: AdCampaingService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdminBoTestModule],
                declarations: [AdCampaingDetailComponent],
                providers: [
                    AdCampaingService
                ]
            })
            .overrideTemplate(AdCampaingDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AdCampaingDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdCampaingService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new AdCampaing(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.adCampaing).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
