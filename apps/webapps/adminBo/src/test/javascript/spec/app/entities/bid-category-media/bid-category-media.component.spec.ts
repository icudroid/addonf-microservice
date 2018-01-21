/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { AdminBoTestModule } from '../../../test.module';
import { BidCategoryMediaComponent } from '../../../../../../main/webapp/app/entities/bid-category-media/bid-category-media.component';
import { BidCategoryMediaService } from '../../../../../../main/webapp/app/entities/bid-category-media/bid-category-media.service';
import { BidCategoryMedia } from '../../../../../../main/webapp/app/entities/bid-category-media/bid-category-media.model';

describe('Component Tests', () => {

    describe('BidCategoryMedia Management Component', () => {
        let comp: BidCategoryMediaComponent;
        let fixture: ComponentFixture<BidCategoryMediaComponent>;
        let service: BidCategoryMediaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdminBoTestModule],
                declarations: [BidCategoryMediaComponent],
                providers: [
                    BidCategoryMediaService
                ]
            })
            .overrideTemplate(BidCategoryMediaComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BidCategoryMediaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BidCategoryMediaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new BidCategoryMedia(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.bidCategoryMedias[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
